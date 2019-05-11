package ru.tokens.site.entities;

import java.io.Serializable;
import java.security.Principal;
import javax.servlet.http.HttpSession;

/**
 *
 * @author solon4ak
 */
public class UserPrincipal implements Principal, Cloneable, Serializable {

    // ------------------------------ FIELDS ------------------------------
    /**
     * Константа для незарегистрированного пользователя.
     */
//    private final String username;
    private final User user;
    private static final UserPrincipal nullUserPrincipal = new UserPrincipal(null);

    // -------------------------- STATIC METHODS --------------------------
    public static UserPrincipal getPrincipal(final User user) {
        return user == null ? nullUserPrincipal : new UserPrincipal(user);
    }

    // --------------------------- CONSTRUCTORS ---------------------------
    public UserPrincipal(final User user) {
        super();
        this.user = user;
    }

    // ------------------------ CANONICAL METHODS ------------------------
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof UserPrincipal)) {
            return false;
        }
        final UserPrincipal o = (UserPrincipal) obj;
        final User myUser = this.getUser();
        final User oUser = o.getUser();
        if (myUser == null && oUser == null) {
            return true;
        } else if (myUser != null && oUser != null) {
            return myUser.equals(oUser);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode() + (this.user == null ? 13 : this.user.hashCode());
    }

    // ------------------------ INTERFACE METHODS ------------------------
    // --------------------- Interface Nullable ---------------------
    public boolean isNull() {
        return this.user == null;
    }

    // --------------------- Interface Principal ---------------------
//    @Override
//    public String getName() {
//        return this.getClass().getName() + " " 
//                + (this.user == null ? "unregistered" : this.getUser().toString());
//    }
    
    @Override
    public String getName() {
        return String.valueOf(this.user.getUserId());
    }

    // -------------------------- OTHER METHODS --------------------------
    public User getUser() {
        return this.user;
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected UserPrincipal clone() {
        try {
            return (UserPrincipal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

    @Override
    public String toString() {
        return this.user.toString();
    }

    public static Principal getPrincipal(HttpSession session) {
        return session == null ? null
                : (Principal) session.getAttribute("ru.tkn.user.principal");
    }

    public static void setPrincipal(HttpSession session, Principal principal) {
        session.setAttribute("ru.tkn.user.principal", principal);
    }

}
