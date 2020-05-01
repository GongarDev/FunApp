
package admin;

import view.VentanaPrincipal;

/**
 *
 * @author melkart
 */
public class FunApp {
    
        public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}
