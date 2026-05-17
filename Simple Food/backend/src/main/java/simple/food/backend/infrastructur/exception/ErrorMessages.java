package simple.food.backend.infrastructur.exception;

public enum ErrorMessages {
    EMPLOYEE_NOT_FOUND_BY_EMAIL("Funcionário não encontrado com email: %s"),
    SERVICE_ERROR("Service error: %s"),
    VALIDATION_ERROR("Validation error: %s"),
    UNAUTHORIZED("Sem autorização para acessar este recurso"),
    INVALID_TOKEN("Token inválido ou expirado"),
    RESOURCE_NOT_FOUND("Recurso não encontrado: %s"),
    INTERNAL_ERROR("Error interno: %s"),
    EMAIL_OR_WHATSAPP_NUMBER_ALREADY_EXISTS("Email ou número de WhatsApp já estão em uso");

    private final String template;

    ErrorMessages(String template) {
        this.template = template;
    }

    public String getMessage(Object... args) {
        if (args == null || args.length == 0) {
            return template;
        }
        return String.format(template, args);
    }
}

