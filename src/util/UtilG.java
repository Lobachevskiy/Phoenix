/*
 * Copyright (C) 2015 joulupunikki joulupunikki@gmail.communist.invalid.
 *
 *  Disclaimer of Warranties and Limitation of Liability.
 *
 *     The creators and distributors offer this software as-is and
 *     as-available, and make no representations or warranties of any
 *     kind concerning this software, whether express, implied, statutory,
 *     or other. This includes, without limitation, warranties of title,
 *     merchantability, fitness for a particular purpose, non-infringement,
 *     absence of latent or other defects, accuracy, or the presence or
 *     absence of errors, whether or not known or discoverable.
 *
 *     To the extent possible, in no event will the creators or distributors
 *     be liable on any legal theory (including, without limitation,
 *     negligence) or otherwise for any direct, special, indirect,
 *     incidental, consequential, punitive, exemplary, or other losses,
 *     costs, expenses, or damages arising out of the use of this software,
 *     even if the creators or distributors have been advised of the
 *     possibility of such losses, costs, expenses, or damages.
 *
 *     The disclaimer of warranties and limitation of liability provided
 *     above shall be interpreted in a manner that, to the extent possible,
 *     most closely approximates an absolute disclaimer and waiver of
 *     all liability.
 *
 */
package util;

import game.Game;
import gui.Gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalSliderUI;
import phoenix.Phoenix;

/**
 * Graphics related utilities.
 * <p>
 * Methods for getting children of JComponents named
 * <pre> XXX getDescendantsOfType(YYY) </pre> are from
 * <a href=https://tips4java.wordpress.com/2008/11/13/swing-utils/>SwingUtils</a>
 * by Darryl Burke.
  * 
 *
 * @author joulupunikki
 */
public class UtilG {

    public static int[] scaleColorsToDark(double r, byte[][] rgb_data) {
        int[] ret_val = new int[rgb_data[0].length];
        double[][] rgb_val = new double[rgb_data[0].length][rgb_data.length];
        for (int i = 0; i < rgb_data[0].length; i++) {
            for (int j = 0; j < rgb_data.length; j++) {
                rgb_val[i][j] = (rgb_data[2 - j][i] & 0xff); // / 255;

            }

        }

        for (int i = 0; i < rgb_val.length; i++) {
            double[] a = rgb_val[i];

            double[] d = new double[3];
            System.arraycopy(a, 0, d, 0, a.length);
            vecMulScalar(d, r);

            int index = 0;
            double shortest = Double.POSITIVE_INFINITY;
            double distance = Double.POSITIVE_INFINITY;
            for (int j = 0; j < rgb_val.length; j++) {
                distance = distBetwPoints(d, rgb_val[j]);
                if (distance < shortest) {
                    shortest = distance;
                    index = j;
                }
            }
            ret_val[i] = index;

        }
        return ret_val;
    }

    public static void writeStruct(int x, int y, int[] pixel_data,
            int[][] structures, int city_no, WritableRaster wr, WindowSize ws) {

        int dx = 0;
        int dy = 0;

        for (int i = 0; i < C.STRUCT_BIN_HEIGHT; i++) {
            for (int j = 0; j < C.STRUCT_BIN_WIDTH; j++) {

                dx = x + j;
                dy = y + i;

                writePixel(dx, dy, i * C.STRUCT_BIN_WIDTH + j,
                        pixel_data, structures, city_no, wr, ws);

            }
        }
    }

    public static void writePixel(int x, int y, int t_idx, int[] pixel_data, int[][] structures,
            int city_no, WritableRaster wr, WindowSize ws) {

        pixel_data[0] = structures[city_no][t_idx];

        if (pixel_data[0] != 0) {
            // if double window size scale image
            if (ws.is_double) {
                wr.setPixel(2 * x, 2 * y, pixel_data);
                wr.setPixel(2 * x + 1, 2 * y, pixel_data);
                wr.setPixel(2 * x, 2 * y + 1, pixel_data);
                wr.setPixel(2 * x + 1, 2 * y + 1, pixel_data);
            } else {
                wr.setPixel(x, y, pixel_data);
            }
        }
    }

    /**
     * Multiplies vector v with scalar c
     *
     * @param v
     * @param c
     */
    public static void vecMulScalar(double[] v, double c) {
        for (int i = 0; i < v.length; i++) {
            v[i] = c * v[i];
        }
    }

    /**
     * Subtracts vector b from vector a
     *
     * @param a
     * @param b
     * @return
     */
    public static double[] vecSub(double[] a, double[] b) {
        double[] d = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            d[i] = a[i] - b[i];
        }
        return d;
    }

    /**
     * Adds vector b to vector a
     *
     * @param a
     * @param b
     * @return
     */
    public static double[] vecAdd(double[] a, double[] b) {
        double[] d = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            d[i] = a[i] + b[i];
        }
        return d;
    }

    /**
     * Calculates distance of point p to unit line (x=y=z)
     *
     * @param p
     * @return
     */
    public static double distPointUnitLine(double[] p) {
        double d = Math.pow(p[0] - p[1], 2)
                + Math.pow(p[1] - p[2], 2)
                + Math.pow(p[2] - p[0], 2);

        d = d / 3;
        d = Math.sqrt(d);
        return d;
    }

    public static double[] closestPointPandUL2(double[] p) {
        double[] ret_val = new double[p.length];
        double d = 0;
        for (int i = 0; i < ret_val.length; i++) {
            d += p[i];
        }
        d = d / 3;
        for (int i = 0; i < ret_val.length; i++) {
            ret_val[i] = d;
        }
        return ret_val;
    }

    /**
     * Closest point on unit line (x=y=z) to point p.
     *
     * @param p
     * @return
     */
    public static double[] closestPointPandUL(double[] p) {
        double[] q = new double[p.length];
        double a = 3;
        double b = -2 * (p[0] + p[1] + p[2]);
        double c = -distPointUnitLine(p);
        System.out.println("c = " + c);

        for (int i = 0; i < q.length; i++) {
            c += Math.pow(p[i], 2);
        }
        double[] r = solveQuadratic(a, b, c);
        double[] x0 = {r[0], r[0], r[0]};
        double[] x1 = {r[1], r[1], r[1]};
        System.out.print("x0 = ");
        for (int j = 0; j < x0.length; j++) {
            System.out.print(x0[j] + " ");

        }
        System.out.println("");
        System.out.print("x1 = ");
        for (int j = 0; j < x1.length; j++) {
            System.out.print(x1[j] + " ");

        }
        System.out.println("");
        System.exit(0);
        if (0 < (distBetwPoints(p, x0) - distBetwPoints(p, x1))) {
            q = x0;
        } else {
            q = x1;
        }

        return q;
    }

    /**
     * Calculates distance between points p and q
     *
     * @param p
     * @param q
     * @return
     */
    public static double distBetwPoints(double[] p, double[] q) {
        double d = 0;
        for (int i = 0; i < q.length; i++) {
            d += Math.pow(p[i] - q[i], 2);
        }
        return Math.sqrt(d);
    }

    /**
     * Solves quadratic ax^2 + bx + c = 0
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        double[] p = new double[2];

        double d = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        p[0] = (-b + d) / (2 * a);
        p[1] = (-b - d) / (2 * a);

        return p;
    }

    public static void setDialogLocation(JDialog dialog, Component component) {
        Dimension d_size = dialog.getSize();
        Dimension c_size = component.getSize();
        dialog.setLocation((c_size.width - d_size.width) / 2, (c_size.height - d_size.height) / 2);
    }

    /**
     * Draws a yellow rectangle
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public static void drawFrameRectIn(Graphics g, int x, int y, int w, int h) {
        x++;
        y++;
        w -= 2;
        h -= 2;
        drawFrameRect(g, x, y, w, h);
    }

    public static void drawFrameRect(Graphics g, int x, int y, int w, int h) {
        g.setColor(C.COLOR_GOLD_DARK);
        g.drawLine(x - 1, y + h, x + w, y + h);
        g.drawLine(x + w, y - 1, x + w, y + h);
        g.setColor(C.COLOR_GOLD_BRIGHT);
        g.drawLine(x - 1, y - 1, x + w, y - 1);
        g.drawLine(x - 1, y - 1, x - 1, y + h);
    }

    public static int center(Graphics g, int x, int w, Font f, String s) {
        FontMetrics fm = g.getFontMetrics(f);
        return x + (w - fm.stringWidth(s)) / 2;
    }

    public static int centerY(Graphics g, int y, int h, Font f) {
        FontMetrics fm = g.getFontMetrics(f);
        return y - (h - fm.getAscent()) / 2;
    }

    public static void drawStringGrad(Graphics2D g2d, String s, Font f, int x, int y) {
        drawStringGrad(g2d, s, f, x, y, 0);
    }

    public static void drawStringGrad(Graphics2D g2d, String s, Font f, int x, int y, int border) {
        //gradient font test
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                    RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(f);
        int line_height = f.getSize();
        GradientPaint gp = new GradientPaint(0, y - line_height + 1, C.COLOR_GOLD_BRIGHT, 0, y, C.COLOR_GOLD_DARK, true);
        if (border > 0) {
            drawStringBorder(g2d, s, Color.BLACK, x, y, border);
        }
        g2d.setPaint(gp);
        g2d.drawString(s, x, y);
    }

    public static void drawStringBorder(Graphics g, String s, Color c, int x, int y, int w) {
        g.setColor(c);
        for (; w > 0; w--) {
            g.drawString(s, x - w, y);
            g.drawString(s, x, y - w);
            g.drawString(s, x + w, y);
            g.drawString(s, x, y + w);
        }

    }

    /**
     * Convenience method for searching below <code>container</code> in the
     * component hierarchy and return nested components that are instances of
     * class <code>clazz</code> it finds. Returns an empty list if no such
     * components exist in the container.
     * <P>
     * Invoking this method with a class parameter of JComponent.class will
     * return all nested components.
     * <P>
     * This method invokes getDescendantsOfType(clazz, container, true)
     * <p>
     * Note: This method should be called under AWT tree lock.
     *
     * @param <T>
     * @param clazz the class of components whose instances are to be found.
     * @param container the container at which to begin the search
     * @return the List of components
     */
    public static <T extends JComponent> List<T> getDescendantsOfType(
            Class<T> clazz, Container container) {
        return getDescendantsOfType(clazz, container, true);
    }

    /**
     * Convenience method for searching below <code>container</code> in the
     * component hierarchy and return nested components that are instances of
     * class <code>clazz</code> it finds. Returns an empty list if no such
     * components exist in the container.
     * <P>
     * Invoking this method with a class parameter of JComponent.class will
     * return all nested components.
     * <p>
     * Note: This method should be called under AWT tree lock.
     *
     * @param <T>
     * @param clazz the class of components whose instances are to be found.
     * @param container the container at which to begin the search
     * @param nested true to list components nested within another listed
     * component, false otherwise
     * @return the List of components
     */
    public static <T extends JComponent> List<T> getDescendantsOfType(
            Class<T> clazz, Container container, boolean nested) {
        List<T> tList = new ArrayList<>();
        for (Component component : container.getComponents()) {
            if (clazz.isAssignableFrom(component.getClass())) {
                tList.add(clazz.cast(component));
            }
            if (nested || !clazz.isAssignableFrom(component.getClass())) {
                tList.addAll(UtilG.<T>getDescendantsOfType(clazz, (Container) component, nested));
         }
      }
      return tList;
    }

    public static <T extends JComponent> Set<Container> findParentsOf(Class<T> clazz, Container container) {
        Container parent = container;
        List<Container> child_list = new LinkedList<>();
        List<Container> parent_list = new LinkedList<>();
        Set<Container> parents = new LinkedHashSet<>();
        Component[] children = null;
        synchronized (container.getTreeLock()) {
            children = container.getComponents();
        }
        for (Component component : children) {
            child_list.add((Container) component);
            parent_list.add(container);
        }
        int count = 1;
        while (!child_list.isEmpty()) {
            System.out.println("Finding parents " + count++);
            Container child = child_list.remove(0);
            parent = parent_list.remove(0);
            if (clazz.isAssignableFrom(child.getClass())) {
                parents.add(parent);
            }
            synchronized (child.getTreeLock()) {
                children = child.getComponents();
            }
            for (Component tmp : children) {
                child_list.add((Container) tmp);
                parent_list.add(child);
            }
        }

        return parents;
    }

    public static void setJComponentChildrenToDark(JComponent chooser) {
        List<JComponent> c_list;
        //tried this with 1 ms delay
        Util.DeadLockGuard guard = Util.getDeadLockGuard(1, chooser);
        guard.start();
        synchronized (chooser.getTreeLock()) {
            c_list = UtilG.getDescendantsOfType(JComponent.class, chooser);
        }
        guard.stopGuard();
        System.out.println("# of " + chooser.getClass().getCanonicalName() + " children : " + c_list.size());
        for (JComponent jc : c_list) {
            jc.setBackground(Color.BLACK);
            if (jc instanceof JPanel) {
                jc.setBackground(Color.DARK_GRAY);
            }
            jc.setForeground(C.COLOR_GOLD);
        }
    }

    public static void setUIDefaults() {
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("OptionPane.foreground", C.COLOR_GOLD);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("Panel.foreground", C.COLOR_GOLD);
        UIManager.put("OptionPane.messageForeground", C.COLOR_GOLD);
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", C.COLOR_GOLD);
        UIManager.put("Button.border", new BorderUIResource(new LineBorder(C.COLOR_GOLD)));
        UIManager.put("Dialog.background", Color.BLACK);
        UIManager.put("Dialog.foreground", C.COLOR_GOLD);
        UIManager.put("ProgressBar.foreground", C.COLOR_GOLD);
        UIManager.put("ProgressBar.background", Color.BLACK);
        UIManager.put("MenuItem.background", Color.BLACK);
        UIManager.put("MenuItem.foreground", C.COLOR_GOLD);
        UIManager.put("MenuItem.border", new BorderUIResource(new LineBorder(C.COLOR_GOLD, 0)));
        UIManager.put("Menu.background", Color.BLACK);
        UIManager.put("Menu.foreground", C.COLOR_GOLD);
        UIManager.put("Menu.border", new BorderUIResource(new LineBorder(C.COLOR_GOLD, 0)));
        UIManager.put("TextArea.background", Color.BLACK);
        UIManager.put("TextArea.foreground", C.COLOR_GOLD);
        UIManager.put("TextField.border", new BorderUIResource(new LineBorder(Color.DARK_GRAY, 0)));
        UIManager.put("TextField.background", Color.BLACK);
        UIManager.put("TextField.foreground", C.COLOR_GOLD);
        UIManager.put("Label.foreground", C.COLOR_GOLD);
//        UIManager.put("Slider.foreground", C.COLOR_GOLD);
//        UIManager.put("Slider.background", C.COLOR_GOLD);
//        UIManager.put("Slider.focus", C.COLOR_GOLD);
//        UIManager.put("Slider.highlight", C.COLOR_GOLD);
//        UIManager.put("Slider.thumb", C.COLOR_GOLD);
//        UIManager.put("Slider.background", C.COLOR_GOLD);
        UIManager.put("Slider.foreground", C.COLOR_GOLD);
        UIManager.put("Slider.focus", C.COLOR_GOLD);
        UIManager.put("Slider.highlight", C.COLOR_GOLD);
        UIManager.put("Slider.shadow", C.COLOR_GOLD);
        UIManager.put("Slider.background", C.COLOR_GOLD);
        UIManager.put("MenuBar.background", Color.BLACK);
        UIManager.put("MenuBar.foreground", Color.BLACK);
    }

    public static void emperorCrowned(Graphics2D g, Game game, WindowSize ws) {
        int emperor = game.getRegency().getCrownedEmperor();
        if (emperor > -1) {
            String s = "Lord of " + Util.getFactionName(emperor) + " has been crowned Emperor of the Fading Suns";
            UtilG.drawStringGrad(g, s, ws.font_large, 5, 5 + ws.font_large.getSize(), 1);
        }
    }

    public static class DarkTheme extends DefaultMetalTheme {

        public String getName() {
            return "DarkTheme";
        }

        private final ColorUIResource primary1 = new ColorUIResource(255, 255, 0);
        private final ColorUIResource primary2 = new ColorUIResource(0, 255, 255);
        private final ColorUIResource primary3 = new ColorUIResource(255, 0, 255);
        private final ColorUIResource menuSelectedBackground = new ColorUIResource(C.COLOR_GOLD);
        private final ColorUIResource menuSelectedForeground = new ColorUIResource(getBlack());
        private final ColorUIResource separatorBackground = new ColorUIResource(C.COLOR_GOLD_DARK);
        private final ColorUIResource separatorForeground = new ColorUIResource(getBlack());
        protected ColorUIResource getPrimary1() {
            return primary1;
        }

        protected ColorUIResource getPrimary2() {
            return primary2;
        }

        protected ColorUIResource getPrimary3() {
            return primary3;
        }

        @Override
        public ColorUIResource getMenuSelectedBackground() {
            return menuSelectedBackground; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ColorUIResource getMenuSelectedForeground() {
            return menuSelectedForeground; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ColorUIResource getSeparatorBackground() {
            return separatorBackground; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ColorUIResource getSeparatorForeground() {
            return separatorForeground; //To change body of generated methods, choose Tools | Templates.
        }


    }

    /**
     * Better colors for JSliders
     */
    public static class DarkSliderUI extends MetalSliderUI {

        GradientPaint gp;
        Color thumb_color;
        long count = 0;

        public DarkSliderUI() {
            super();
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            if (slider.isEnabled()) {
                thumb_color = C.COLOR_GOLD;
//                gp = gp_on;
//                g.setColor(slider.getBackground());
            } else {
                thumb_color = C.COLOR_GOLD_DARK;
//                gp = gp_off;
//                g.setColor(slider.getBackground().darker());
            }
            super.paint(g, c); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void paintTrack(Graphics g) {
            Rectangle trackBounds = trackRect;

            int cy = (trackBounds.height / 2) - 2;
            int cw = trackBounds.width;

            g.translate(trackBounds.x, trackBounds.y + cy);

            g.setColor(getShadowColor());
            g.drawLine(0, 0, cw - 1, 0);
            g.drawLine(0, 1, 0, 2);
            g.setColor(getHighlightColor());
            g.drawLine(0, 3, cw, 3);
            g.drawLine(cw, 0, cw, 3);
            g.setColor(Color.black);
            g.drawLine(1, 1, cw - 2, 1);

            g.translate(-trackBounds.x, -(trackBounds.y + cy));
        }

        @Override
        public void paintThumb(Graphics g) {
            //System.out.println("paintThumb count " + ++count);
            Rectangle knobBounds = thumbRect;
            int w = knobBounds.width;
            int h = knobBounds.height;
            if (gp == null) {
                int y = knobBounds.y;
                gp = new GradientPaint(0, y, C.COLOR_GOLD_BRIGHT, 0, y - h + 1, C.COLOR_GOLD_DARK, true);
            }
            g.translate(knobBounds.x, knobBounds.y);



            // "plain" version
            if (slider.isEnabled()) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            } else {
                g.setColor(thumb_color);
                g.fillRect(0, 0, w, h);
            }
            g.setColor(Color.black);
            g.drawLine(0, h - 1, w - 1, h - 1);
            g.drawLine(w - 1, 0, w - 1, h - 1);

            g.setColor(getHighlightColor());
            g.drawLine(0, 0, 0, h - 2);
            g.drawLine(1, 0, w - 2, 0);

            g.setColor(getShadowColor());
            g.drawLine(1, h - 2, w - 2, h - 2);
            g.drawLine(w - 2, 1, w - 2, h - 3);

            g.translate(-knobBounds.x, -knobBounds.y);
        }

    }

    public static class PhoenixJOptionPane extends JOptionPane {


        private static final long serialVersionUID = 1L;
        private static byte[][] pallette;
        private static WindowSize ws;

        /**
         *
         * @param message the value of message
         * @param message_type the value of message_type
         * @param option_type the value of option_type
         * @param icon the value of icon
         * @param options the value of options
         * @param initial_value the value of initial_value
         */
        public PhoenixJOptionPane(Object message, int message_type, int option_type, Icon icon, Object[] options, Object initial_value) {
            super(message, message_type, option_type, icon, options);
            List<JComponent> descendantsOfType = null;
            synchronized (this.getTreeLock()) {
                descendantsOfType = UtilG.getDescendantsOfType(JComponent.class, this, true);
            }
            for (JComponent jc : descendantsOfType) {
                System.out.println(jc.getClass() + "\n   " + jc);
                if (jc instanceof JPanel) {
                    ((JPanel) jc).setOpaque(false);
                }
            }
            this.setOpaque(false);
        }

        @Override
        public void paint(Graphics g) {
            BufferedImage bi = Util.loadImage(FN.S_BG0_PCX, ws.is_double, pallette, 640, 480);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(bi, null, 0, 0);
            
            Phoenix.logger.debug(this.toString());
            //super.paint(g);
            super.paint(g);
        }

        public static void setUpPhoenixJOptionPane(Gui gui) {
            pallette = gui.getPallette();
            ws = Gui.getWindowSize();
        }

//        private JDialog createDialog(Component parentComponent, String title,
//                int style)
//                throws HeadlessException {
//
//            final JDialog dialog;
//
//            Window window = PhoenixJOptionPane.getWindowForComponent(parentComponent);
//            if (window instanceof Frame) {
//                dialog = new JDialog((Frame) window, title, true);
//            } else {
//                dialog = new JDialog((Dialog) window, title, true);
//            }
//            if (window instanceof SwingUtilities.SharedOwnerFrame) {
//                WindowListener ownerShutdownListener
//                        = SwingUtilities.getSharedOwnerFrameShutdownListener();
//                dialog.addWindowListener(ownerShutdownListener);
//            }
//            super.initDialog(dialog, style, parentComponent);
//            return dialog;
//        }

//        static Window getWindowForComponent(Component parentComponent)
//                throws HeadlessException {
//            if (parentComponent == null) {
//                return getRootFrame();
//            }
//            if (parentComponent instanceof Frame || parentComponent instanceof Dialog) {
//                return (Window) parentComponent;
//            }
//            return PhoenixJOptionPane.getWindowForComponent(parentComponent.getParent());
//
//        }
        
    }

//    private static class PhoenixJDialog extends JDialog {
//
//        private static byte[][] pallette;
//        private static WindowSize ws;
//
//        @Override
//        public void paint(Graphics g) {
//            super.paint(g);
//
//            BufferedImage bi = Util.loadImage(FN.S_BG0_PCX, ws.is_double, pallette, 640, 480);
//            Graphics2D g2d = (Graphics2D) g;
//
//            g2d.drawImage(bi, null, 0, 0);
//        }
//
//        public static void setUpPhoenixJDialog(Gui gui) {
//            pallette = gui.getPallette();
//            ws = Gui.getWindowSize();
//        }
//
//    }

    public static void setUpUtilG(Gui gui) {
        PhoenixJOptionPane.setUpPhoenixJOptionPane(gui);
    }

}