package pinsgatherer;

public interface FlashBootServices {

    /**
     * This methodregisters a user with the given mail and dni.
     * http://www.elsantoregalapines.com/ page must be opened in a browser.
     * @param dni DNI field
     * @param mail Mail field
     * @return true if no errors ocurred, false otherwise.
     */
    boolean register(String dni, String mail);
}
