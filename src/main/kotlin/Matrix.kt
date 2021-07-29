data class Matrix<T>(
	private val data: List<MutableList<T>> = emptyList()
) {

	val rows = data.size
	val columns = data.getOrNull(0)?.size ?: 0

	val rowIndices = data.indices
	val columnIndices = data[0].indices

	constructor(vararg rows: List<T>) : this(rows.map { it.toMutableList() })

	constructor(rows: Int, columns: Int, init: (Int, Int) -> T) : this(List(rows) { i ->
		MutableList(columns) { j -> init(i, j) }
	})

	init {
		checkNotVariableNumberOfColumns()
	}

	private fun checkNotVariableNumberOfColumns() {
		data.forEach { require(it.size == columns) { "Size of columns is different!" } }
	}

	operator fun get(i: Int, j: Int): T {
		ensureInRange(i, j)
		return data[i][j]
	}

	operator fun set(i: Int, j: Int, value: T) {
		ensureInRange(i, j)
		data[i][j] = value
	}

	private fun ensureInRange(i: Int, j: Int) {
		require(i in data.indices) { "$i not in range $rowIndices" }
		require(j in data[i].indices) { "$j not in range $columnIndices" }
	}

	override fun toString(): String =
		data.joinToString(separator = ",${System.lineSeparator()}", prefix = "[", postfix = "]")
}
