package utils

class Fixtures {
    companion object {
        fun readJson(path: String): String = this::class.java.getResource(path)!!.readText()
    }
}