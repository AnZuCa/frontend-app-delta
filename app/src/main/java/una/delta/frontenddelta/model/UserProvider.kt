package una.delta.frontenddelta.model

import java.util.*

class UserProvider {
    companion object {
        fun findUserById(id: Int) : UserResult {
            return userList[id]
        }

        fun findAllUser() : List<UserResult> {
            return userList
        }

        val userList = listOf<UserResult>(
            UserResult(
                1,
                "1",
                "Andres",
                "assaas",
                "andres@gmail.com",
                true,
                false,
                Date(),
                "Alajuela",
                "84256565",
                emptyList()
            ),
            UserResult(
                2,
                "2",
                "jose",
                "pablo",
                "josepablo@gmail.com",
                true,
                false,
                Date(),
                "Heredia",
                "842565675",
                emptyList()
            ),
            UserResult(
                3,
                "3",
                "luis",
                "diego",
                "ldiego@gmail.com",
                true,
                false,
                Date(),
                "San jose",
                "84256598",
                emptyList()
            )
        )
    }
}