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
package state;

import galaxyreader.Unit;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import util.C;
import util.C;

/**
 * Planet window stack selected no destination selected.
 *
 * @author joulupunikki
 */
public class PW2 extends PW {

    private static PW2 instance = new PW2();

    public PW2() {
    }

    public static State get() {
        return instance;
    }

    public void clickOnMainMap(MouseEvent e) {

        // if button 3
        //on stack
        //on city
        //on empty hex
        // if button 1        
        //on hex
        Point p = SU.getPlanetMapClickPoint(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            clickOnPlanetMapButton1(p);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            SU.clickOnPlanetMapButton3(p);
        }

    }

//    public void clickOnPlanetWindow(MouseEvent e) {
//        Point p = e.getPoint();
//        if (SU.isOnStackDisplay(p)) {
//            SU.clickOnStackDisplay(e);
//        }
//    }
//    public void clickOnStackDisplay(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1) {
//            SU.clickOnStackDisplayButton1(e);
//        } else if (e.getButton() == MouseEvent.BUTTON3) {
//            // display stack window
////            gui.showStackWindow();
//            SU.showUnitInfoWindow();
//
//        }
//    }
    public void wheelRotated(MouseWheelEvent e) {
        SU.wheelOnPlanetMap(e);
    }

    public void clickOnPlanetMapButton1(Point p) {
        Point q = game.getSelectedPoint();
        List<Unit> stack = game.getPlanetGrid(game.getCurrentPlanetNr()).getHex(q.x, q.y).getStack();
        if (stack.get(0).owner != game.getTurn()) {
            return;
        }
        if (SU.disembarkNavalCargo(p) || SU.embarkNavalCargo(p)) {
            return;
        }
        SU.findPath(p);
        if (game.getPath() != null) {
            gui.setCurrentState(PW3.get());
        }
    }

    @Override
    public void pressLaunchButton() {
        SU.pressLaunchButtonSU();
    }

    @Override
    public void pressBuildButton() {
        SU.pressBuildButtonSU();
    }

    @Override
    public void pressTradeButton() {
        SU.pressTradeButtonSU();
    }

    @Override
    public void razeCity() {
        SU.razeCitySU();
    }

    @Override
    public void pressSelectAllButton() {
        gui.selectUnits(C.S_ALL);
    }

}
