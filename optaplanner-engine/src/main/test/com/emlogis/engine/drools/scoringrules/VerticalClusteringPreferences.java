package com.emlogis.engine.drools.scoringrules;

import com.emlogis.engine.domain.*;
import com.emlogis.engine.domain.solver.RuleName;
import com.emlogis.engine.drools.ConstraintTesterBase;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.optaplanner.core.api.score.buildin.bendable.BendableScoreHolder;
import org.optaplanner.core.impl.score.director.drools.DroolsScoreDirector;

import static org.junit.Assert.assertEquals;

public class VerticalClusteringPreferences extends ConstraintTesterBase {

    @Override
    protected void loadRosterInfo() {
        ShiftDate planningWindowStart = new ShiftDate(new LocalDate(2014, 5, 27));
        rosterInfo.setFirstShiftDate(planningWindowStart.getDateOfFirstDayOfWeek(rosterInfo.getFirstDayOfWeek()));
        rosterInfo.setPlanningWindowStart(planningWindowStart);
        rosterInfo.setLastShiftDate(rosterInfo.getFirstShiftDate().plusDays(7));
        kSession.insert(rosterInfo.getFirstShiftDateOn(rosterInfo.getFirstDayOfWeek()));
    }

    
    @Test
    public void testTwoConsecutiveShifts(){
        Employee employee = createEmployee(1, "MR.", "X");
        kSession.insert(employee);

        ShiftType shiftType = createShiftType(1, new LocalTime(9, 0, 0), new LocalTime(15, 0, 0), false);
        kSession.insert(shiftType);
        
        ShiftType shiftTypeSecond = createShiftType(1, new LocalTime(15, 0, 0), new LocalTime(22, 0, 0), false);
        kSession.insert(shiftTypeSecond);

        ShiftDate shiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(shiftDate);

        ShiftDate secondShiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(secondShiftDate);

        ShiftDate thirdShiftDate = new ShiftDate(new LocalDate(2014, 5, 29));
        kSession.insert(thirdShiftDate);

        ShiftDate fourthShiftDate = new ShiftDate(new LocalDate(2014, 5, 30));
        kSession.insert(fourthShiftDate);

        Shift shift = createShift("1", 0, shiftDate, shiftType, 1);
        kSession.insert(shift);

        Shift secondShift = createShift("2", 0, secondShiftDate, shiftTypeSecond, 1);
        kSession.insert(secondShift);

        Shift thirdShift = createShift("3", 0, thirdShiftDate, shiftType, 1);
        kSession.insert(thirdShift);

        Shift fourthShift = createShift("4", 0, fourthShiftDate, shiftType, 1);
        kSession.insert(fourthShift);

        ShiftAssignment shiftAssignment = createShiftAssignment(employee, 0, shift);
        kSession.insert(shiftAssignment);

        ShiftAssignment secondShiftAssignment = createShiftAssignment(employee, 0, secondShift);
        kSession.insert(secondShiftAssignment);

        ShiftAssignment thirdShiftAssignment = createShiftAssignment(null, 0, thirdShift);
        kSession.insert(thirdShiftAssignment);

        ShiftAssignment fourthShiftAssignment = createShiftAssignment(null, 0, fourthShift);
        kSession.insert(fourthShiftAssignment);

        kSession.fireAllRules();

        BendableScoreHolder scoreHolder = (BendableScoreHolder) kSession.getGlobal(DroolsScoreDirector.GLOBAL_SCORE_HOLDER_KEY);
        int numOfConsecDaysConstraints = getNumOfConstraintMatches(RuleName.VERTICAL_CLUSTERING_RULE, scoreHolder.getConstraintMatchTotals());
        assertEquals(0, numOfConsecDaysConstraints);
    }
    
    @Test
    public void testTwoNonConsecutiveSameDayShiftsThirtyMin(){
        Employee employee = createEmployee(1, "MR.", "X");
        kSession.insert(employee);

        ShiftType shiftType = createShiftType(1, new LocalTime(9, 0, 0), new LocalTime(15, 0, 0), false);
        kSession.insert(shiftType);
        
        ShiftType shiftTypeSecond = createShiftType(1, new LocalTime(15, 30, 0), new LocalTime(22, 0, 0), false);
        kSession.insert(shiftTypeSecond);

        ShiftDate shiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(shiftDate);

        ShiftDate secondShiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(secondShiftDate);

        Shift shift = createShift("1", 0, shiftDate, shiftType, 1);
        kSession.insert(shift);

        Shift secondShift = createShift("2", 0, secondShiftDate, shiftTypeSecond, 1);
        kSession.insert(secondShift);

        ShiftAssignment shiftAssignment = createShiftAssignment(employee, 0, shift);
        kSession.insert(shiftAssignment);

        ShiftAssignment secondShiftAssignment = createShiftAssignment(employee, 0, secondShift);
        kSession.insert(secondShiftAssignment);

        kSession.fireAllRules();

        BendableScoreHolder scoreHolder = (BendableScoreHolder) kSession.getGlobal(DroolsScoreDirector.GLOBAL_SCORE_HOLDER_KEY);
        int numOfConsecDaysConstraints = getNumOfConstraintMatches(RuleName.VERTICAL_CLUSTERING_RULE, scoreHolder.getConstraintMatchTotals());
        assertEquals(-1, numOfConsecDaysConstraints);
    }
    
    @Test
    public void testTwoNonConsecutiveSameDayShiftsFourHours(){
        Employee employee = createEmployee(1, "MR.", "X");
        kSession.insert(employee);

        ShiftType shiftType = createShiftType(1, new LocalTime(9, 0, 0), new LocalTime(15, 0, 0), false);
        kSession.insert(shiftType);
        
        ShiftType shiftTypeSecond = createShiftType(1, new LocalTime(19, 00, 0), new LocalTime(22, 0, 0), false);
        kSession.insert(shiftTypeSecond);

        ShiftDate shiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(shiftDate);

        ShiftDate secondShiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(secondShiftDate);

        Shift shift = createShift("1", 0, shiftDate, shiftType, 1);
        kSession.insert(shift);

        Shift secondShift = createShift("2", 0, secondShiftDate, shiftTypeSecond, 1);
        kSession.insert(secondShift);

        ShiftAssignment shiftAssignment = createShiftAssignment(employee, 0, shift);
        kSession.insert(shiftAssignment);

        ShiftAssignment secondShiftAssignment = createShiftAssignment(employee, 0, secondShift);
        kSession.insert(secondShiftAssignment);

        kSession.fireAllRules();

        BendableScoreHolder scoreHolder = (BendableScoreHolder) kSession.getGlobal(DroolsScoreDirector.GLOBAL_SCORE_HOLDER_KEY);
        int numOfConsecDaysConstraints = getNumOfConstraintMatches(RuleName.VERTICAL_CLUSTERING_RULE, scoreHolder.getConstraintMatchTotals());
        assertEquals(-3, numOfConsecDaysConstraints);
    }
    
    @Test
    public void testTwoNonConsecutiveSameDayShiftsSevenHours(){
        Employee employee = createEmployee(1, "MR.", "X");
        kSession.insert(employee);

        ShiftType shiftType = createShiftType(1, new LocalTime(9, 0, 0), new LocalTime(14, 0, 0), false);
        kSession.insert(shiftType);
        
        ShiftType shiftTypeSecond = createShiftType(1, new LocalTime(21, 00, 0), new LocalTime(22, 0, 0), false);
        kSession.insert(shiftTypeSecond);

        ShiftDate shiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(shiftDate);

        ShiftDate secondShiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(secondShiftDate);

        Shift shift = createShift("1", 0, shiftDate, shiftType, 1);
        kSession.insert(shift);

        Shift secondShift = createShift("2", 0, secondShiftDate, shiftTypeSecond, 1);
        kSession.insert(secondShift);

        ShiftAssignment shiftAssignment = createShiftAssignment(employee, 0, shift);
        kSession.insert(shiftAssignment);

        ShiftAssignment secondShiftAssignment = createShiftAssignment(employee, 0, secondShift);
        kSession.insert(secondShiftAssignment);

        kSession.fireAllRules();

        BendableScoreHolder scoreHolder = (BendableScoreHolder) kSession.getGlobal(DroolsScoreDirector.GLOBAL_SCORE_HOLDER_KEY);
        int numOfConsecDaysConstraints = getNumOfConstraintMatches(RuleName.VERTICAL_CLUSTERING_RULE, scoreHolder.getConstraintMatchTotals());
        assertEquals(-4, numOfConsecDaysConstraints);
    }
    
    @Test
    public void testTwoNonConsecutiveDifferentDaysShifts(){
        Employee employee = createEmployee(1, "MR.", "X");
        kSession.insert(employee);

        ShiftType shiftType = createShiftType(1, new LocalTime(9, 0, 0), new LocalTime(15, 0, 0), false);
        kSession.insert(shiftType);
        
        ShiftType shiftTypeSecond = createShiftType(1, new LocalTime(15, 30, 0), new LocalTime(22, 0, 0), false);
        kSession.insert(shiftTypeSecond);

        ShiftDate shiftDate = new ShiftDate(new LocalDate(2014, 5, 27));
        kSession.insert(shiftDate);

        ShiftDate secondShiftDate = new ShiftDate(new LocalDate(2014, 5, 28));
        kSession.insert(secondShiftDate);

        ShiftDate thirdShiftDate = new ShiftDate(new LocalDate(2014, 5, 29));
        kSession.insert(thirdShiftDate);

        ShiftDate fourthShiftDate = new ShiftDate(new LocalDate(2014, 5, 30));
        kSession.insert(fourthShiftDate);

        Shift shift = createShift("1", 0, shiftDate, shiftType, 1);
        kSession.insert(shift);

        Shift secondShift = createShift("2", 0, secondShiftDate, shiftTypeSecond, 1);
        kSession.insert(secondShift);

        Shift thirdShift = createShift("3", 0, thirdShiftDate, shiftType, 1);
        kSession.insert(thirdShift);

        Shift fourthShift = createShift("4", 0, fourthShiftDate, shiftType, 1);
        kSession.insert(fourthShift);

        ShiftAssignment shiftAssignment = createShiftAssignment(employee, 0, shift);
        kSession.insert(shiftAssignment);

        ShiftAssignment secondShiftAssignment = createShiftAssignment(employee, 0, secondShift);
        kSession.insert(secondShiftAssignment);

        ShiftAssignment thirdShiftAssignment = createShiftAssignment(null, 0, thirdShift);
        kSession.insert(thirdShiftAssignment);

        ShiftAssignment fourthShiftAssignment = createShiftAssignment(null, 0, fourthShift);
        kSession.insert(fourthShiftAssignment);

        kSession.fireAllRules();

        BendableScoreHolder scoreHolder = (BendableScoreHolder) kSession.getGlobal(DroolsScoreDirector.GLOBAL_SCORE_HOLDER_KEY);
        int numOfConsecDaysConstraints = getNumOfConstraintMatches(RuleName.VERTICAL_CLUSTERING_RULE, scoreHolder.getConstraintMatchTotals());
        assertEquals(0, numOfConsecDaysConstraints);
    }
  
}
