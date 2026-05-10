package code.view.menu;

//data strucutre import
import java.util.List;
//model import
import code.model.puzzle.PresettedPassword;
import code.model.puzzle.PuzzlePiece;
import code.view.images.StaticImage;
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
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PuzzleMenu extends JPanel
{
    private static final DataFlavor PIECE_FLAVOR;
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
    	private static final int GRID_WIDTH = 2;
    	private static final int GRID_HEIGHT = 2;
    	private static final int BORDER_SIZE = 2;
    	
        public GridPanel()
        {
            this.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT));
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_SIZE));
            this.setPreferredSize(new Dimension(GRID_WIDTH * SlotPanel.SIZE, GRID_WIDTH * SlotPanel.SIZE));

            for(int i = 1; i <= GRID_WIDTH * GRID_HEIGHT; i++)
            	this.add(new SlotPanel(piecesContainer));
        }
    }
    
    private class SlotPanel extends JPanel
    {
    	public static final int SIZE = 40;
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
        
        closeButton.setFont(customFont);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        
        northPanel.add(passwordLabel, BorderLayout.CENTER);
        northPanel.add(closeButton, BorderLayout.EAST); 

        JPanel centerPanel = new JPanel();
        JPanel gridsPanel = new JPanel(new GridLayout(1, 9, 5, 15));
        piecesContainer = new JPanel(new GridLayout(0, 6, 4, 4));
        JScrollPane scrollPane = new JScrollPane(piecesContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        for (int i = 1; i < 10; i++)
        	gridsPanel.add(new GridPanel());
        
        for (int i = 0; i < puzzlePieces.size(); i++) 
            piecesContainer.add(createPieceLabel(StaticImage.getPuzzlePiece(puzzlePieces.get(i)).getImage()));

        scrollPane.setPreferredSize(new Dimension(0, 160));
        scrollPane.setBorder(new LineBorder(new Color(80, 80, 120), 1));

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(gridsPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scrollPane);
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Conferma");
        
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
        
        new DropTarget(piecesContainer, new PieceDropTargetListener(null, piecesContainer));
    }

    private JLabel createPieceLabel(BufferedImage image) 
    {
    	Image scaledImage = image.getScaledInstance(SlotPanel.SIZE, SlotPanel.SIZE, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        
        label.setPreferredSize(new Dimension(SlotPanel.SIZE, SlotPanel.SIZE));

        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer
        (
        		label,
                DnDConstants.ACTION_MOVE,
                dge -> dge.startDrag(DragSource.DefaultMoveDrop, new PieceTransferable(label))
        );
        
        return label;
    }
}