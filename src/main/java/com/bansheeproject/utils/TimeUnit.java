package com.bansheeproject.utils;

import java.util.Calendar;


/**
 * A helper enum to ease time handling. 
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public enum TimeUnit {
	
	MILISSECONDS {
		@Override
		public long getModifier() {			
			return 1;
		}

		@Override
		public void add(Calendar calendar, int amount) {
			calendar.add(Calendar.MILLISECOND, amount);
			
		}
	}, SECONDS {
		@Override
		public long getModifier() {
			return 1000;
		}

		@Override
		public void add(Calendar calendar, int amount) {
			calendar.add(Calendar.SECOND, amount);
			
		}
	},
	
	MINUTES {

		@Override
		public long getModifier() {
			return SECONDS.getModifier() * 60;
		}

		@Override
		public void add(Calendar calendar, int amount) {
			calendar.add(Calendar.MINUTE, amount);
			
		}
		
	},
	
	HOURS {

		@Override
		public long getModifier() {

			return MINUTES.getModifier() * 60;
		}

		@Override
		public void add(Calendar calendar, int amount) {
			calendar.add(Calendar.HOUR, amount);
			
		}
		
	},
	
	DAYS {

		@Override
		public long getModifier() {

			return HOURS.getModifier() * 24;
		}

		@Override
		public void add(Calendar calendar, int amount) {
			calendar.add(Calendar.DAY_OF_MONTH, amount);
			
		}
		
	};
	
	
	public abstract long getModifier();
	
	public abstract void add(Calendar calendar, int amount);
	
	public long multiply (long amount ) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount of time cannot be less than zero");
		}
		
		return amount * getModifier();
	}
	
}
