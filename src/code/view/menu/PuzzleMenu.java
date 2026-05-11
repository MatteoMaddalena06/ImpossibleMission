package code.view.menu;

//data strucutre import
import java.util.List;
//event import
import code.event.EventDispatcher;
//view import
import code.view.images.StaticImage;
import code.view.menu.event.PuzzleMenuClosed;
//model import
import code.model.puzzle.PresettedPassword;
import code.model.puzzle.PuzzlePiece;
import code.model.context.GameWillEnd;
//graphics import
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PuzzleMenu extends JPanel
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;
	private static final int PERMOUSEWHEEL_PIXEL = 16;
	
    private static final DataFlavor PIECE_FLAVOR;
    
    private final GridPanel[] gridPanels = new GridPanel[PresettedPassword.SIZE];
    private final JPanel piecesContainer;

    static 
    {
        DataFlavor f = null;
        
        try
        { 
        	f = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=javax.swing.JLabel");
        } 
        catch (ClassNotFoundException e)
        { e.printStackTrace(); }
        
        PIECE_FLAVOR = f;
    }
    
    private class GridPanel extends JPanel 
    {
    	private static final int WIDTH = 2;
    	private static final int HEIGHT = 2;
    	private static final int BORDER_SIZE = 2;
    	
    	private final SlotPanel[][] slots = new SlotPanel[HEIGHT][WIDTH];
    	
        public GridPanel()
        {
            this.setLayout(new GridLayout(WIDTH, HEIGHT));
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_SIZE));
            this.setPreferredSize(new Dimension(WIDTH * SlotPanel.SIZE, WIDTH * SlotPanel.SIZE));

            for(int i = 0; i < HEIGHT; i++)
            {
            	for(int j = 0; j < WIDTH; j++)
            	{
	            	slots[i][j] = new SlotPanel(piecesContainer);
	            	this.add(slots[i][j]);
            	}
            }
        }
        
        private SlotPanel[][] getSlots()
        { return slots; }
    }
    
    private class SlotPanel extends JPanel
    {
    	private static final int SIZE = 40;
    	private static final int BORDER_SIZE = 1;
    	
        public SlotPanel(JPanel piecesContainer)
        {
            this.setLayout(new GridBagLayout());
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_SIZE));
            this.setPreferredSize(new Dimension(SIZE, SIZE));

            new DropTarget(this, new PieceDropTargetListener(this, piecesContainer));
        }

        boolean isEmpty() 
        { return this.getComponentCount() == 0; }
    }
    
    private class PieceTransferable implements Transferable 
    {
        private final JLabel label;

        public PieceTransferable(JLabel label)
        { this.label = label; }

        @Override
        public DataFlavor[] getTransferDataFlavors()
        { return new DataFlavor[]{ PIECE_FLAVOR }; }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor)
        { return PIECE_FLAVOR.equals(flavor); }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
        {
            if(!isDataFlavorSupported(flavor))
            	throw new UnsupportedFlavorException(flavor);
            
            return label;
        }
    }

    private class PieceDropTargetListener extends DropTargetAdapter 
    {
        private final SlotPanel targetSlot;      
        private final JPanel piecesContainer;

        public PieceDropTargetListener(SlotPanel targetSlot, JPanel piecesContainer) 
        {
            this.targetSlot = targetSlot;
            this.piecesContainer = piecesContainer;
        }

        @Override
        public void dragEnter(DropTargetDragEvent dtde) 
        {
            if(canAccept()) 
            	dtde.acceptDrag(DnDConstants.ACTION_MOVE);
            else            
            	dtde.rejectDrag();
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde)
        {
            if(canAccept()) 
            	dtde.acceptDrag(DnDConstants.ACTION_MOVE);
            else             
            	dtde.rejectDrag();
        }

        @Override
        public void drop(DropTargetDropEvent dtde)
        {
            try
            {
                if(!dtde.getTransferable().isDataFlavorSupported(PIECE_FLAVOR)) 
                {
                    dtde.rejectDrop();
                    return;
                }

                dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                JLabel piece = (JLabel) dtde.getTransferable().getTransferData(PIECE_FLAVOR);

                Container parent = piece.getParent();
                
                if(parent != null) 
                {
                    parent.remove(piece);
                    parent.revalidate();
                    parent.repaint();
                }

                if(targetSlot != null)
                {
                    targetSlot.add(piece);
                    targetSlot.revalidate();
                    targetSlot.repaint();
                }
                else
                {
                    piecesContainer.add(piece);
                    piecesContainer.revalidate();
                    piecesContainer.repaint();
                }

                dtde.dropComplete(true);

            } catch (Exception ex) 
            { dtde.rejectDrop();}
        }

        private boolean canAccept() 
        { return targetSlot == null || targetSlot.isEmpty(); }
    }
    
    public PuzzleMenu(PresettedPassword password, List<PuzzlePiece> puzzlePieces, Font customFont)
    {
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel(password.getPassword(), SwingConstants.CENTER);
        JButton closeButton = new JButton("X"); 
        
        passwordLabel.setFont(customFont);
        
        closeButton.setFocusPainted(false);
        closeButton.setFont(customFont);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        
        northPanel.add(passwordLabel, BorderLayout.CENTER);
        northPanel.add(closeButton, BorderLayout.EAST); 

        JPanel centerPanel = new JPanel();
        JPanel gridsPanel = new JPanel(new GridLayout(1, PresettedPassword.SIZE, 5, 15));
        piecesContainer = new JPanel(new GridLayout(0, 6, 4, 4));
        JScrollPane scrollPane = new JScrollPane(piecesContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        for(int i = 0; i < PresettedPassword.SIZE; i++)
        {
            gridPanels[i] = new GridPanel();
            gridsPanel.add(gridPanels[i]);
        }
        
        for(int i = 0; i < puzzlePieces.size(); i++) 
            piecesContainer.add(createPieceLabel(puzzlePieces.get(i)));

        scrollPane.setPreferredSize(new Dimension(0, 160));
        scrollPane.setBorder(new LineBorder(new Color(80, 80, 120), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(PERMOUSEWHEEL_PIXEL);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(gridsPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scrollPane);
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Conferma");
        
        submitButton.setFocusPainted(false);
        submitButton.setFont(customFont);
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        southPanel.add(submitButton);
        
        this.setLayout(new BorderLayout(8, 8));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        
        closeButton.addActionListener(e -> EventDispatcher.notify(new PuzzleMenuClosed()));
        submitButton.addActionListener(e -> { 
        	if(password.checkAttempt(getGridsContent()))
        		EventDispatcher.notify(new GameWillEnd(GameWillEnd.NOW));
        });
        
        new DropTarget(piecesContainer, new PieceDropTargetListener(null, piecesContainer));
    }

    private JLabel createPieceLabel(PuzzlePiece puzzlePiece) 
    {
    	Image scaledImage = StaticImage.getPuzzlePiece(puzzlePiece).getImage().getScaledInstance(SlotPanel.SIZE, SlotPanel.SIZE, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        
        label.setPreferredSize(new Dimension(SlotPanel.SIZE, SlotPanel.SIZE));
        label.putClientProperty("puzzlePiece", puzzlePiece);

        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer
        (
        		label,
                DnDConstants.ACTION_MOVE,
                dge -> dge.startDrag(DragSource.DefaultMoveDrop, new PieceTransferable(label))
        );
        
        return label;
    }
    
    private PuzzlePiece[][][] getGridsContent()
    {
    	PuzzlePiece[][][] result = new PuzzlePiece[PresettedPassword.SIZE][GridPanel.HEIGHT][GridPanel.WIDTH];
    	
    	for(int i = 0; i < result.length; i++)
    	{
    		for(int j = 0; j < result[0].length; j++)
    		{
    			for(int k = 0; k < result[0][0].length; k++)
    			{
    				try
    				{
    					JLabel puzzleLabel = (JLabel)gridPanels[i].getSlots()[j][k].getComponent(0);
    					result[i][j][k] = (PuzzlePiece)puzzleLabel.getClientProperty("puzzlePiece");
    				}
    				catch(ArrayIndexOutOfBoundsException e)
    				{ result[i][j][k] = null; }
    			}
    		}
    	}
    	
    	return result;
    }
    		
    public void setPositionInFrame(int frameWidth, int frameHeight)
	{ this.setBounds((frameWidth - WIDTH) / 2, (frameHeight - HEIGHT) / 2, WIDTH, HEIGHT); }
}