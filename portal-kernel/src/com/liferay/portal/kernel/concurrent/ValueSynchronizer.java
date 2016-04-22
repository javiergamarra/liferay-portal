package com.liferay.portal.kernel.concurrent;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * @author Carlos Sierra Andrés
 */
public class ValueSynchronizer<T> {

	public ValueSynchronizer() {
		_createConsumer = Optional.empty();

		_clearConsumer = Optional.empty();
	}

	public ValueSynchronizer(Consumer<T> onCreate, Consumer<T> onClear) {
		_createConsumer = Optional.ofNullable(onCreate);

		_clearConsumer = Optional.ofNullable(onClear);
	}

	public void clear(T resource) {
		Object lock = _resourceLocks.get(resource);

		if (lock != null) {
			synchronized (lock) {
				if (_clearConsumer != null) {
					_clearConsumer.ifPresent(c -> c.accept(resource));
				}

				_resourceLocks.remove(resource, lock);
			}
		}
	}

	public void close() {
		if (_closed) {
			return;
		}

		_closed = true;

		if (_clearConsumer.isPresent()) {
			_resourceLocks.forEach((r, l) -> clear(r));
		}
	}

	public void synchronizeOn(T resource, Consumer<T> consumer) {
		if (_closed) {
			return;
		}

		Object lock = _resourceLocks.get(resource);

		boolean creation = false;

		if (lock == null) {
			Object newLock = new Object();

			lock = _resourceLocks.putIfAbsent(resource, newLock);

			if (lock == null) {
				lock = newLock;

				creation = true;
			}
		}

		synchronized (lock) {
			if (lock != _resourceLocks.get(resource)) {
				return;
			}

			if (creation) {
				_createConsumer.ifPresent(c -> c.accept(resource));
			}

			consumer.accept(resource);
		}
	}

	public ValueSynchronizer<T> onCreateResource(Consumer<T> createConsumer) {
		_createConsumer = Optional.ofNullable(createConsumer);

		return this;
	}

	public ValueSynchronizer<T> onClearResource(Consumer<T> createConsumer) {
		_clearConsumer = Optional.ofNullable(createConsumer);

		return this;
	}

	private Optional<Consumer<T>> _clearConsumer;

	private Optional<Consumer<T>> _createConsumer;

	private final ConcurrentMap<T, Object> _resourceLocks =
		new ConcurrentHashMap<>();

	private volatile boolean _closed = false;

}