package com.afrimoov.sqlitest.models

import junit.framework.Assert.assertEquals
import org.junit.Test

class ListItemTest{

    private val employee : ListItem.Employee = ListItem.Employee(
        id = 1,
        firstName = "ARNO",
        lastName = "ABOMO",
        avatar = "",
        email = "abomoarno@gmail.com"
    )

    @Test
    fun testName(){
        assertEquals(employee.firstName, "ARNO")
    }

    @Test
    fun testModification(){
        employee.firstName = "STEPHANE"
        assertEquals(employee.firstName, "STEPHANE")
    }

}