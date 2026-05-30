package co.edu.udea.calidad.paquetrack.tasks;

import co.edu.udea.calidad.paquetrack.utils.Memory;
import co.edu.udea.calidad.paquetrack.utils.TokenCache;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Task de precondicion: deja al actor con una sesion valida para las credenciales
 * dadas. Si ya existe un token en cache lo reutiliza (evita autenticarse de nuevo
 * y respeta el rate limiting del auth-service); si no, inicia sesion realmente.
 *
 * Usar SOLO como contexto previo. Cuando el inicio de sesion es la accion bajo
 * prueba (validar roles, credenciales o aprobacion), usar {@link Authenticate}.
 */
public class ResumeSession implements Task {

    private final String email;
    private final String password;

    public ResumeSession(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static ResumeSession forCredentials(String email, String password) {
        return instrumented(ResumeSession.class, email, password);
    }

    @Override
    @Step("{0} cuenta con una sesion activa como #email")
    public <T extends Actor> void performAs(T actor) {
        String cachedToken = TokenCache.get(email);
        if (cachedToken != null) {
            actor.remember(Memory.ACCESS_TOKEN, cachedToken);
        } else {
            actor.attemptsTo(Authenticate.withCredentials(email, password));
        }
    }
}
