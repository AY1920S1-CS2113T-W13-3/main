package duke.model.lists;

import duke.commons.exceptions.DukeException;

/**
 * Interface - Models a List of planning objects.
 */
interface Listable<T> {
    void add(T item) throws DukeException;

    boolean isEmpty();

    boolean contains(T item);

    int size();

    T get(int index) throws IndexOutOfBoundsException;
}
