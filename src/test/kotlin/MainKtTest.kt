import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class MainKtTest {

	@ParameterizedTest
	@MethodSource("isWordInMatrixTestCases")
	fun isWordInMatrix(case: IsWordInMatrixTestCase) = with(case) {
		assertEquals(expected, isWordInMatrix(target, matrix))
	}

	@ParameterizedTest
	@MethodSource("matrixContainsAdjacentSubstringTestCases")
	fun matrixContainsAdjacentSubstring(case: MatrixContainsAdjacentSubstringTestCase) = with(case) {
		assertEquals(expected, matrixContainsAdjacentSubstring(target, coordinates, matrix, visited))
	}

	@ParameterizedTest
	@MethodSource("adjacentCharsTestCases")
	fun adjacentChars(case: AdjacentCharsTestCases) = with(case) {
		assertEquals(expected, adjacentChars(char, coordinates, matrix))
	}


	companion object {

		@JvmStatic
		fun isWordInMatrixTestCases(): List<Arguments> {
			val matrix = Matrix(
				"ABC".toList(),
				"DEF".toList(),
				"GHC".toList()
			)
			val base = IsWordInMatrixTestCase(
				target = "ABCFE",
				matrix = matrix,
				expected = true
			)
			return listOf(
				base,
				base.copy(
					target = "ABEDA",
					expected = false
				),
				base.copy(
					target = "ABF",
					expected = false
				)
			).map { arguments(it) }
		}

		@JvmStatic
		fun matrixContainsAdjacentSubstringTestCases(): List<Arguments> {
			val matrix = Matrix(
				"ABC".toList(),
				"DEF".toList(),
				"GHC".toList()
			)
			val visited = Matrix(matrix.rows, matrix.columns) { _, _ -> false }
			val base = MatrixContainsAdjacentSubstringTestCase(
				target = "BCFE",
				coordinates = 0 to 0,
				matrix = matrix,
				visited = visited,
				expected = true
			)
			return listOf(
				base,
				base.copy(
					target = "ABEDA",
					expected = false
				),
				base.copy(
					target = "ABF",
					expected = false
				)
			).map { arguments(it) }
		}

		@JvmStatic
		fun adjacentCharsTestCases(): List<Arguments> {
			val matrix = Matrix(
				"ABC".toList(),
				"DEF".toList(),
				"GHC".toList()
			)

			val base = AdjacentCharsTestCases(
				char = 'A',
				coordinates = 0 to 0,
				matrix = matrix,
				expected = listOf()
			)

			return listOf(
				base,
				base.copy(
					char = 'B',
					expected = listOf(0 to 1)
				),
				base.copy(
					char = 'C',
					coordinates = 1 to 2,
					expected = listOf(
						0 to 2,
						2 to 2
					)
				),
			).map { arguments(it) }
		}
	}
}

data class IsWordInMatrixTestCase(
	val target: String,
	val matrix: Matrix<Char>,
	val expected: Boolean
)

data class MatrixContainsAdjacentSubstringTestCase(
	val target: String,
	val coordinates: Pair<Int, Int>,
	val matrix: Matrix<Char>,
	val visited: Matrix<Boolean>,
	val expected: Boolean
)

data class AdjacentCharsTestCases(
	val char: Char,
	val coordinates: Pair<Int, Int>,
	val matrix: Matrix<Char>,
	val expected: List<Pair<Int, Int>>
)
