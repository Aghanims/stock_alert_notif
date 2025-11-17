package edu.gmu.cs321;

import java.util.Optional;

/**
 * Interface for User data access operations.
 *
 * @author Giorgi
 * @version 1.0
 */
public interface UserDao {
    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Saves a new or existing user.
     *
     * @param user The User object to save.
     */
    void save(User user);
}
