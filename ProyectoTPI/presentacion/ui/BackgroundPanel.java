package ProyectoTPI.presentacion.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private Image imagenFondo;

    public BackgroundPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        cargarImagenFondo();
    }

    private void cargarImagenFondo() {
        try {
            ImageIcon icon = new ImageIcon("ProyectoTPI/recursos/imagenes/fondo_utn.jpg");
            if (icon.getIconWidth() > 0) {
                imagenFondo = icon.getImage();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                             RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (imagenFondo != null) {
            int imgW = imagenFondo.getWidth(this);
            int imgH = imagenFondo.getHeight(this);
            double scale = Math.max((double) getWidth() / imgW, (double) getHeight() / imgH);
            int scaledW = (int) (imgW * scale);
            int scaledH = (int) (imgH * scale);
            int x = (getWidth() - scaledW) / 2;
            int y = (getHeight() - scaledH) / 2;
            g2d.drawImage(imagenFondo, x, y, scaledW, scaledH, this);
            g2d.setColor(ColorScheme.OVERLAY_OSCURO);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g2d.setColor(ColorScheme.NEGRO_FONDO);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}