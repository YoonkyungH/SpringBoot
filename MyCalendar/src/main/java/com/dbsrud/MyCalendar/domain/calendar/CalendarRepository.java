package com.dbsrud.MyCalendar.domain.calendar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
