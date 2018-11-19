package com.mwaltman.devops.framework.externalapi;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Adapted from {@link org.apache.http.impl.client.BasicCookieStore} (default
 * implementation of {@link CookieStore}). This implementation adds the ability
 * to remove individual cookies and to add cookies from a {@link List} or
 * another {@link CookieStore}.
 */
@Contract(threading = ThreadingBehavior.SAFE)
public final class CookieMonster implements CookieStore, Serializable {

    /**
     * The set that holds all the {@link Cookie cookies} for this instance.
     */
    private final TreeSet<Cookie> cookies;

    /**
     * The lock to use when reading from/writing to {@link #cookies}
     */
    private final ReadWriteLock lock;

    public CookieMonster() {
        this.cookies = new TreeSet<>(new CookieIdentityComparator());
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Add a {@link Cookie}, replacing any existing equivalent cookies. If the
     * given cookie has already expired it will not be added, but existing
     * equivalent cookies will still be removed.
     *
     * @param cookie The cookie to add
     *
     * @exception NullPointerException If {@code cookie} is {@code null}
     *
     * @see #addCookies(Cookie[]) Add an array of cookies
     * @see #addCookies(List)  Add a {@link List} of cookies
     * @see #addCookies(CookieStore) Add cookies from another store
     */
    @Override
    public void addCookie(final Cookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("Cookie cannot be null");
        }

        lock.writeLock().lock();
        try {
            cookies.remove(cookie);
            if (!cookie.isExpired(new Date())) {
                cookies.add(cookie);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Add an array of {@link Cookie cookies}. Cookies are added individually
     * and in the given array order. If any of the given cookies have already
     * expired, they will not be added, but existing values will still be
     * removed.
     *
     * @param cookies The cookies to be added
     *
     * @exception NullPointerException If {@code cookies} is {@code null}
     *
     * @see #addCookie(Cookie) Add a single cookie
     */
    public void addCookies(final Cookie[] cookies) {
        if (cookies == null) {
            throw new NullPointerException("Cookies cannot be null");
        }

        lock.writeLock().lock();
        try {
            for (final Cookie aCookie : cookies) {
                this.cookies.remove(aCookie);
                if (!aCookie.isExpired(new Date())) {
                    this.cookies.add(aCookie);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Add a {@link List} of {@link Cookie cookies}. Cookies are added
     * individually and in the given list order. If any of the given cookies
     * have already expired, they will not be added, but existing values will
     * still be removed.
     *
     * @param cookies The cookies to be added
     *
     * @exception NullPointerException If {@code cookies} is {@code null}
     */
    public void addCookies(final List<Cookie> cookies) {
        if (cookies == null) {
            throw new NullPointerException("Cookies cannot be null");
        }
        addCookies((Cookie[])cookies.toArray());
    }

    /**
     * Add {@link Cookie cookies} from a {@link CookieStore}. Cookies are added
     * individually and in the order returned by {@link CookieStore#getCookies()}.
     * If any of the given cookies have already expired, they will not be added,
     * but existing values will still be removed.
     *
     * @param cookieStore The cookie store to add cookies from
     *
     * @exception NullPointerException If {@code cookieStore} is {@code null}
     */
    public void addCookies(final CookieStore cookieStore) {
        if (cookieStore == null) {
            throw new NullPointerException("Cookie store cannot be null");
        }
        addCookies(cookieStore.getCookies());
    }

    /**
     * Remove a {@link Cookie}.
     *
     * @param cookie Cookie to remove
     *
     * @return {@code true} if the cookie was present and removed, {@code false}
     * otherwise
     *
     * @exception NullPointerException If {@code cookie} is {@code null}
     */
    public boolean removeCookie(final Cookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("Cookie cannot be null");
        }
        lock.writeLock().lock();
        try {
            return this.cookies.remove(cookie);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Get a copy of {@link Cookie cookies} currently stored in this instance
     * as {@link List}.
     *
     * @return A copy of the cookies in this instance
     */
    @Override
    public List<Cookie> getCookies() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(cookies);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Remove all {@link Cookie cookies} stored in this instance that have
     * expired as of the specified {@link Date}.
     *
     * @param date The date to test for
     *
     * @return {@code true} if any cookies were removed
     */
    @Override
    public boolean clearExpired(Date date) {
        if (date == null) {
            return false;
        }
        lock.writeLock().lock();
        try {
            boolean removed = false;
            for (final Iterator<Cookie> it = cookies.iterator(); it.hasNext();) {
                if (it.next().isExpired(date)) {
                    it.remove();
                    removed = true;
                }
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Remove all {@link Cookie cookies} stored in this instance.
     */
    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            cookies.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        try {
            return "CookieMonster{" +
                    "cookies=" + cookies +
                    '}';
        } finally {
            lock.readLock().unlock();
        }
    }
}
