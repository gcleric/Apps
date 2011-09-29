/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evedata;

/**
 * Interface for alarm objects.
 * @author hrivanov
 */
public interface Alarm
{
  /**
   * Checks if there is an alarm condition.
   * @return 0 - no alarm, 1 - warning alarm, 2 - urgent alarm.
   */
  int checkForAlarm();
  
  /**
   * Sets a time period in hours, below which the alarm becomes active.
   * @param warningHours Time period in hours, below which a warning alarm level is set.
   * @param urgentHours Time period in hours, below which an urgent alarm level is set.
   */
  void setAlarmHours(int warningHours, int urgentHours);
  
}
