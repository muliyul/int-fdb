package com.muliyul.fdb

fun isWordInMatrix(target: String, matrix: Matrix<Char>): Boolean {
	for (r in matrix.rowIndices) {
		for (c in matrix.columnIndices) {
			val visited = Matrix(matrix.rows, matrix.columns) { _, _ -> false }

			if (
				matrix[r, c] == target.getOrNull(0) && // we found starting point, now lets check adjacent cells
				matrixContainsAdjacentSubstring(
					target = target.drop(1),
					startCoordinates = r to c,
					matrix = matrix,
					visited = visited
				)
			) return true
		}
	}
	return false
}

/**
 * Returns true if the matrix contains adjacent substring
 */
fun matrixContainsAdjacentSubstring(
	target: String,
	startCoordinates: Pair<Int, Int>,
	matrix: Matrix<Char>,
	visited: Matrix<Boolean>
): Boolean {
	if (target.isEmpty()) return true

	val (r, c) = startCoordinates
	visited[r, c] = true

	val possibilities =
		adjacentChars(target[0], startCoordinates, matrix)
			.filter { (i, j) -> !visited[i, j] }

	return possibilities.isNotEmpty() && possibilities.any {

		// TODO: convert this recursive function to iterative by managing a stack.
		matrixContainsAdjacentSubstring(
			target = target.drop(1),
			startCoordinates = it,
			matrix = matrix,
			visited = visited
		)
	}
}

/**
 * returns the coordinates of the adjacent [coords] if character is [c] or empty if none exist.
 */
fun adjacentChars(c: Char, coords: Pair<Int, Int>, matrix: Matrix<Char>): List<Pair<Int, Int>> {
	val rows = matrix.rowIndices
	val columns = matrix.columnIndices

	val steps = listOf(
		0 to -1,
		0 to 1,
		-1 to 0,
		1 to 0
	)
	val legalCells = steps.mapNotNull { step ->
		val i = coords.first + step.first
		val j = coords.second + step.second
		when {
			(i in rows) && (j in columns) -> coords + step
			else -> null
		}
	}

	return legalCells.filter { (i, j) -> matrix[i, j] == c }
}

/**
 * "Vector" addition
 */
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
	(first + other.first) to (second + other.second)

