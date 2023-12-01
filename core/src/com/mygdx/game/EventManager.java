package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager {
	// a map storing lists of event handlers (consumers) for each EventType
	// allows for registering multiple handlers per event
	private static Map<EventTypes, List<Consumer<Object>>> listeners = new HashMap<>();
	
	public static void subscribe(EventTypes eventType, Consumer<Object> listener) {
		// add the listener to the list for the given eventType
		// if no list exists for this eventType, create a new list first
		listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
	}
	
	public static void notify(EventTypes eventType, Object data) {
		if (listeners.containsKey(eventType)) {
			// iterate through and execute each listener associated with the eventType passing in the event data
			for (Consumer<Object> listener : listeners.get(eventType)) {
				listener.accept(data);
			}
		}
	}
}
