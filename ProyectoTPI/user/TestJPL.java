package ProyectoTPI.user;

import org.jpl7.Query;
import org.jpl7.Term;
import java.util.Map;

public class TestJPL {
    public static void main(String[] args) {
        System.out.println("=============================");
        System.out.println("   PROBANDO JPL - PROYECTI");
        System.out.println("=============================\n");
        
        try {
            // Test 1: Operación simple
            System.out.println("Test 1: Cálculo matemático");
            Query q1 = new Query("X is 10 + 5");
            
            if (q1.hasSolution()) {
                Map<String, Term> resultado = q1.oneSolution();
                System.out.println("✅ 10 + 5 = " + resultado.get("X"));
            } else {
                System.out.println("❌ Error en el cálculo");
            }
            
            // Test 2: Versión de Prolog
            System.out.println("\nTest 2: Versión de Prolog");
            Query q2 = new Query("current_prolog_flag(version, V)");
            
            if (q2.hasSolution()) {
                System.out.println("✅ Versión: " + q2.oneSolution().get("V"));
            }
            
            System.out.println("\n=============================");
            System.out.println("   🎉 ¡JPL FUNCIONA!");
            System.out.println("=============================");
            
        } catch (UnsatisfiedLinkError e) {
            System.err.println("\n❌ ERROR: No se encontró la biblioteca nativa");
            System.err.println("Solución: Agrega al PATH:");
            System.err.println("  Windows: C:\\Program Files\\swipl\\bin");
            System.err.println("  Linux: /usr/lib/swi-prolog/bin");
            System.err.println("\nLuego reinicia VS Code");
            
        } catch (NoClassDefFoundError e) {
            System.err.println("\n❌ ERROR: No se encontró jpl.jar");
            System.err.println("Verifica que:");
            System.err.println("  1. jpl.jar está en lib/");
            System.err.println("  2. settings.json está configurado");
            System.err.println("  3. Ejecutaste Clean Workspace");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}