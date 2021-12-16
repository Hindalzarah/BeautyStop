package com.example.beautystop.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class RegistrationTestingUtilTest {

private val registrationTestingUtil = RegistrationTestingUtil()

    //this function makes sure that the username is not an empty string, when it is, it returns False.
    @Test
    fun `empty username returns false`(){

        val result = registrationTestingUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }


    //this test function checks if the username is already taken or not. if it's not taken it returns True.
    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = registrationTestingUtil.validateRegistrationInput(
            "Sara",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    //this test function checks if the username is already taken or not. if it's taken it returns False.

    @Test
    fun `username already exists returns false`() {
        val result = registrationTestingUtil.validateRegistrationInput(
            "Hind",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    //this test function checks if the confirm password == password. if it's not, it returns False.

    @Test
    fun `incorrectly confirmed password returns false`() {
        val result = registrationTestingUtil.validateRegistrationInput(
            "Hind",
            "123456",
            "abcdefg"
        )
        assertThat(result).isFalse()
    }

    //this test function makes sure that the password is written currently and is not empty.

    @Test
    fun `empty password returns false`() {
        val result = registrationTestingUtil.validateRegistrationInput(
            "Hind",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    //this test function makes sure that the password at least contains 2 digits.

    @Test
    fun `less than 2 digit password returns false`() {
        val result = registrationTestingUtil.validateRegistrationInput(
            "Hind",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()
    }

}
