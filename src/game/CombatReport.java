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
package game;

import galaxyreader.Unit;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to record combat data necessary to show combat replays.
 *
 * @author joulupunikki <joulupunikki@gmail.communist.invalid>
 */
public class CombatReport implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public List<Unit> attacker;
    public List<Unit> defender;
    public int[] atk_post_health;
    public boolean[] atk_rout;
    public int[] def_post_health;
    public boolean[] def_rout;

    public CombatReport(int nr_atk, int nr_def) {
        attacker = new LinkedList<>();
        defender = new LinkedList<>();
        atk_post_health = new int[nr_atk];
        atk_rout = new boolean[nr_atk];
        def_post_health = new int[nr_def];
        def_rout = new boolean[nr_def];
    }

}
