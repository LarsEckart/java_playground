package lars.katas.SudokuKata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {

    static void Play() {
        // Prepare empty board
        String line = "+---+---+---+";
        String middle = "|...|...|...|";
        char[][] board = new char[][]
                {
                        line.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        line.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        line.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        middle.toCharArray(),
                        line.toCharArray()
                };

        // Construct board to be solved
        Random rng = new Random(42);

        // Top element is current state of the board
        Stack<int[]> stateStack = new Stack<int[]>();

        // Top elements are (row, col) of cell which has been modified compared to previous state
        Stack<Integer> rowIndexStack = new Stack<Integer>();
        Stack<Integer> colIndexStack = new Stack<Integer>();

        // Top element indicates candidate digits (those with False) for (row, col)
        Stack<Boolean[]> usedDigitsStack = new Stack<Boolean[]>();

        // Top element is the value that was set on (row, col)
        Stack<Integer> lastDigitStack = new Stack<Integer>();

        // Indicates operation to perform next
        // - expand - finds next empty cell and puts new state on stacks
        // - move - finds next candidate number at current pos and applies it to current state
        // - collapse - pops current state from stack as it did not yield a solution
        String command = "expand";

        while (stateStack.size() <= 9 * 9) {
            if (command == "expand") {
                int[] currentState = new int[9 * 9];

                if (stateStack.size() > 0) {
                    System.arraycopy(stateStack.peek(), 0, currentState, 0, currentState.length);
                    //Array.Copy(stateStack.Peek(), currentState, currentState.Length);
                }

                int bestRow = -1;
                int bestCol = -1;
                Boolean[] bestUsedDigits = null;
                int bestCandidatesCount = -1;
                int bestRandomValue = -1;
                Boolean containsUnsolvableCells = false;

                for (int index = 0; index < currentState.length; index++) {
                    if (currentState[index] == 0) {

                        int row = index / 9;
                        int col = index % 9;
                        int blockRow = row / 3;
                        int blockCol = col / 3;

                        Boolean[] isDigitUsed = new Boolean[9];
                        Arrays.fill(isDigitUsed, Boolean.FALSE);

                        for (int i = 0; i < 9; i++) {
                            int rowDigit = currentState[9 * i + col];
                            if (rowDigit > 0) {
                                isDigitUsed[rowDigit - 1] = true;
                            }

                            int colDigit = currentState[9 * row + i];
                            if (colDigit > 0) {
                                isDigitUsed[colDigit - 1] = true;
                            }

                            int blockDigit = currentState[(blockRow * 3 + i / 3) * 9 + (blockCol * 3 + i % 3)];
                            if (blockDigit > 0) {
                                isDigitUsed[blockDigit - 1] = true;
                            }
                        } // for (i = 0..8)

                        //int candidatesCount = isDigitUsed.Where(used => !used).Count();

                        long count = Arrays.stream(isDigitUsed).filter(used -> !used).count();
                        int candidatesCount = Math.toIntExact(count);

                        if (candidatesCount == 0) {
                            containsUnsolvableCells = true;
                            break;
                        }

                        int randomValue = rng.nextInt();

                        if (bestCandidatesCount < 0 ||
                                candidatesCount < bestCandidatesCount ||
                                (candidatesCount == bestCandidatesCount && randomValue < bestRandomValue)) {
                            bestRow = row;
                            bestCol = col;
                            bestUsedDigits = isDigitUsed;
                            bestCandidatesCount = candidatesCount;
                            bestRandomValue = randomValue;
                        }
                    } // for (index = 0..81)
                }

                if (!containsUnsolvableCells) {
                    stateStack.push(currentState);
                    rowIndexStack.push(bestRow);
                    colIndexStack.push(bestCol);
                    usedDigitsStack.push(bestUsedDigits);
                    lastDigitStack.push(0); // No digit was tried at this position
                }

                // Always try to move after expand
                command = "move";
            } // if (command == "expand")
            else if (command == "collapse") {
                stateStack.pop();
                rowIndexStack.pop();
                colIndexStack.pop();
                usedDigitsStack.pop();
                lastDigitStack.pop();

                command = "move";   // Always try to move after collapse
            } else if (command == "move") {

                int rowToMove = rowIndexStack.peek();
                int colToMove = colIndexStack.peek();
                int digitToMove = lastDigitStack.pop();

                int rowToWrite = rowToMove + rowToMove / 3 + 1;
                int colToWrite = colToMove + colToMove / 3 + 1;

                Boolean[] usedDigits = usedDigitsStack.peek();
                int[] currentState = stateStack.peek();
                int currentStateIndex = 9 * rowToMove + colToMove;

                int movedToDigit = digitToMove + 1;
                while (movedToDigit <= 9 && usedDigits[movedToDigit - 1]) {
                    movedToDigit += 1;
                }

                if (digitToMove > 0) {
                    usedDigits[digitToMove - 1] = false;
                    currentState[currentStateIndex] = 0;
                    board[rowToWrite][colToWrite] = '.';
                }

                if (movedToDigit <= 9) {
                    lastDigitStack.push(movedToDigit);
                    usedDigits[movedToDigit - 1] = true;
                    currentState[currentStateIndex] = movedToDigit;
                    board[rowToWrite][colToWrite] = (char) ('0' + movedToDigit);

                    // Next possible digit was found at current position
                    // Next step will be to expand the state
                    command = "expand";
                } else {
                    // No viable candidate was found at current position - pop it in the next iteration
                    lastDigitStack.push(0);
                    command = "collapse";
                }
            } // if (command == "move")
        }

        System.out.println();
        System.out.println("Final look of the solved board:");
        //System.out.println(String.Join(Environment.NewLine, board.Select(s => new String(s)).ToArray()));
        Arrays.stream(board).forEach(s -> System.out.println(s));

        // Generate inital board from the completely solved one
        // Board is solved at this point.
        // Now pick subset of digits as the starting position.
        int remainingDigits = 30;
        int maxRemovedPerBlock = 6;
        int[][] removedPerBlock = new int[3][3];
        int[] positions = IntStream.range(0, 9 * 9).toArray();
        int[] state = stateStack.peek();

        int[] finalState = new int[state.length];
        //Array.Copy(state, finalState, finalState.length);
        System.arraycopy(state, 0, finalState, 0, finalState.length);

        int removedPos = 0;
        while (removedPos < 9 * 9 - remainingDigits) {
            int curRemainingDigits = positions.length - removedPos;
            int indexToPick = removedPos + rng.nextInt(curRemainingDigits);

            int row = positions[indexToPick] / 9;
            int col = positions[indexToPick] % 9;

            int blockRowToRemove = row / 3;
            int blockColToRemove = col / 3;

            if (removedPerBlock[blockRowToRemove][blockColToRemove] >= maxRemovedPerBlock) {
                continue;
            }

            removedPerBlock[blockRowToRemove][blockColToRemove] += 1;

            int temp = positions[removedPos];
            positions[removedPos] = positions[indexToPick];
            positions[indexToPick] = temp;

            int rowToWrite = row + row / 3 + 1;
            int colToWrite = col + col / 3 + 1;

            board[rowToWrite][colToWrite] = '.';

            int stateIndex = 9 * row + col;
            state[stateIndex] = 0;

            removedPos += 1;
        }

        System.out.println();
        System.out.println("Starting look of the board to solve:");
        //System.out.println(String.Join("\n", board.Select(s = > new String(s)).ToArray()));
        Arrays.stream(board).forEach(s -> System.out.println(s));

        // Prepare lookup structures that will be used in further execution
        System.out.println();
        System.out.println(new String("=").repeat(80));
        System.out.println();

        HashMap<Integer, Integer> maskToOnesCount = new HashMap<>();
        maskToOnesCount.put(0, 0);
        for (int i = 1; i < (1 << 9); i++) {
            int smaller = i >> 1;
            int increment = i & 1;
            maskToOnesCount.put(i, maskToOnesCount.get(smaller) + increment);
        }

        HashMap<Integer, Integer> singleBitToIndex = new HashMap<Integer, Integer>();
        for (int i = 0; i < 9; i++) {
            singleBitToIndex.put(1 << i, i);
        }

        int allOnes = (1 << 9) - 1;

        Boolean changeMade = true;
        while (changeMade) {
            changeMade = false;

            // Calculate candidates for current state of the board
            int[] candidateMasks = new int[state.length];

            for (int i = 0; i < state.length; i++) {
                if (state[i] == 0) {

                    int row = i / 9;
                    int col = i % 9;
                    int blockRow = row / 3;
                    int blockCol = col / 3;

                    int colidingNumbers = 0;
                    for (int j = 0; j < 9; j++) {
                        int rowSiblingIndex = 9 * row + j;
                        int colSiblingIndex = 9 * j + col;
                        int blockSiblingIndex = 9 * (blockRow * 3 + j / 3) + blockCol * 3 + j % 3;

                        int rowSiblingMask = 1 << (state[rowSiblingIndex] - 1);
                        int colSiblingMask = 1 << (state[colSiblingIndex] - 1);
                        int blockSiblingMask = 1 << (state[blockSiblingIndex] - 1);

                        colidingNumbers = colidingNumbers | rowSiblingMask | colSiblingMask | blockSiblingMask;
                    }

                    candidateMasks[i] = allOnes & ~colidingNumbers;
                }
            }

            // Build a collection (named cellGroups) which maps cell indices into distinct groups(rows / columns / blocks)

            class Group {

                int discriminator;
                String description;
                int index;
                int row;
                int column;

                public Group(int d, String de, int i, int r, int c) {
                    this.discriminator = d;
                    this.description = de;
                    this.index = i;
                    this.row = r;
                    this.column = c;
                }
            }

            var rowIndices = IntStream
                    .range(0, state.length)
                    .mapToObj(index -> new Group(index / 9, "row " + index / 9 + 1, index, index / 9, index % 9))
                    .collect(Collectors.groupingBy(t -> t.discriminator));

            var columnIndices = IntStream
                    .range(0, state.length)
                    .mapToObj(index -> new Group(9 + index % 9, "column #{index % 9 + 1}", index, index / 9, index % 9))
                    .collect(Collectors.groupingBy(t -> t.discriminator));

            var blockIndices = IntStream
                    .range(0, state.length)
                    .mapToObj(p -> new Object() {
                        int row = p / 9;
                        int column = p % 9;
                        int index = p;
                    })
                    .map(t -> new Group(18 + 3 * (t.row / 3) + t.column / 3, "block ({tuple.Row / 3 + 1}, {tuple.Column / 3 + 1})", t.index,
                            t.row, t.column))
                    .collect(Collectors.groupingBy(t -> t.discriminator));

            List<List<Group>> cellGroups = new ArrayList<>();
            cellGroups.addAll(rowIndices.values());
            cellGroups.addAll(columnIndices.values());
            cellGroups.addAll(blockIndices.values());

            Boolean stepChangeMade = true;
            while (stepChangeMade) {
                stepChangeMade = false;

                // Pick cells with only one candidate left

                var singleCandidateIndices = IntStream
                        .range(0, candidateMasks.length)
                        .mapToObj(i -> new Object() {
                            int candidatesCount = maskToOnesCount.get(candidateMasks[i]);
                            int index = i;
                        })
                        .filter(t -> t.candidatesCount == 1)
                        .mapToInt(t -> t.index)
                        .toArray();

                if (singleCandidateIndices.length > 0) {
                    int pickSingleCandidateIndex = rng.nextInt(singleCandidateIndices.length);
                    int singleCandidateIndex = singleCandidateIndices[pickSingleCandidateIndex];
                    int candidateMask = candidateMasks[singleCandidateIndex];
                    int candidate = singleBitToIndex.get(candidateMask);

                    int row = singleCandidateIndex / 9;
                    int col = singleCandidateIndex % 9;

                    int rowToWrite = row + row / 3 + 1;
                    int colToWrite = col + col / 3 + 1;

                    state[singleCandidateIndex] = candidate + 1;
                    board[rowToWrite][colToWrite] = (char) ('1' + candidate);
                    candidateMasks[singleCandidateIndex] = 0;
                    changeMade = true;

                    System.out.printf("(%s, %s) can only contain %s.\n", row + 1, col + 1, candidate + 1);
                }

                // Try to find a number which can only appear in one place in a row/column / block

                if (!changeMade) {
                    List<String> groupDescriptions = new ArrayList<String>();
                    List<Integer> candidateRowIndices = new ArrayList<>();
                    List<Integer> candidateColIndices = new ArrayList<>();
                    List<Integer> candidates = new ArrayList<>();

                    for (int digit = 1; digit <= 9; digit++) {
                        int mask = 1 << (digit - 1);
                        for (int cellGroup = 0; cellGroup < 9; cellGroup++) {
                            int rowNumberCount = 0;
                            int indexInRow = 0;

                            int colNumberCount = 0;
                            int indexInCol = 0;

                            int blockNumberCount = 0;
                            int indexInBlock = 0;

                            for (int indexInGroup = 0; indexInGroup < 9; indexInGroup++) {
                                int rowStateIndex = 9 * cellGroup + indexInGroup;
                                int colStateIndex = 9 * indexInGroup + cellGroup;
                                int blockRowIndex = (cellGroup / 3) * 3 + indexInGroup / 3;
                                int blockColIndex = (cellGroup % 3) * 3 + indexInGroup % 3;
                                int blockStateIndex = blockRowIndex * 9 + blockColIndex;

                                if ((candidateMasks[rowStateIndex] & mask) != 0) {
                                    rowNumberCount += 1;
                                    indexInRow = indexInGroup;
                                }

                                if ((candidateMasks[colStateIndex] & mask) != 0) {
                                    colNumberCount += 1;
                                    indexInCol = indexInGroup;
                                }

                                if ((candidateMasks[blockStateIndex] & mask) != 0) {
                                    blockNumberCount += 1;
                                    indexInBlock = indexInGroup;
                                }
                            }

                            if (rowNumberCount == 1) {
                                groupDescriptions.add("Row " + cellGroup + 1);
                                candidateRowIndices.add(cellGroup);
                                candidateColIndices.add(indexInRow);
                                candidates.add(digit);
                            }

                            if (colNumberCount == 1) {
                                groupDescriptions.add("Column " + cellGroup + 1);
                                candidateRowIndices.add(indexInCol);
                                candidateColIndices.add(cellGroup);
                                candidates.add(digit);
                            }

                            if (blockNumberCount == 1) {
                                int blockRow = cellGroup / 3;
                                int blockCol = cellGroup % 3;

                                groupDescriptions.add("Block (" + blockRow + 1 + ", " + blockCol + 1 + ")");
                                candidateRowIndices.add(blockRow * 3 + indexInBlock / 3);
                                candidateColIndices.add(blockCol * 3 + indexInBlock % 3);
                                candidates.add(digit);
                            }
                        } // for (cellGroup = 0..8)
                    } // for (digit = 1..9)

                    if (candidates.size() > 0) {
                        int index = rng.nextInt(candidates.size());
                        String description = groupDescriptions.get(index);
                        int row = candidateRowIndices.get(index);
                        int col = candidateColIndices.get(index);
                        int digit = candidates.get(index);
                        int rowToWrite = row + row / 3 + 1;
                        int colToWrite = col + col / 3 + 1;

                        String message = description + " can contain " + digit + " only at (" + row + 1 + ", " + col + 1 + ").";

                        int stateIndex = 9 * row + col;
                        state[stateIndex] = digit;
                        candidateMasks[stateIndex] = 0;
                        board[rowToWrite][colToWrite] = (char) ('0' + digit);

                        changeMade = true;

                        System.out.println(message);
                    }
                }

                // Try to find pairs of digits in the same row / column / block and remove them from other colliding cells
                if (!changeMade) {
                    var twoDigitMasks = IntStream.range(0, candidateMasks.length)
                            .filter(mask -> maskToOnesCount.get(mask) == 2)
                            .distinct()
                            .boxed()
                            .collect(Collectors.toList());

                    //candidateMasks.Where(mask = > maskToOnesCount[mask] == 2).Distinct().ToList();
/*
                    var groups =
                            twoDigitMasks
                                    .SelectMany(mask = >
                                    cellGroups
                                            .Where(group = > group.Count(tuple = > candidateMasks[tuple.Index] == mask) ==2)
                                        .
                    Where(group = > group.Any(tuple = > candidateMasks[tuple.Index] != mask && (candidateMasks[tuple.Index] & mask) > 0))
                                        .Select(group = > new
                    {
                                Mask = mask,
                                Discriminator = group.Key,
                                Description = group.First().Description,
                                Cells = group
                    }))
                                .ToList();
/*
                    if (groups.Any()) {
                        foreach(var group in groups)
                        {
                            var cells =
                                    group.Cells
                                            .Where(
                                                    cell = >
                                            candidateMasks[cell.Index] != group.Mask &&
                                            (candidateMasks[cell.Index] & group.Mask) > 0)
                                        .ToList();

                            var maskCells =
                                    group.Cells
                                            .Where(cell = > candidateMasks[cell.Index] == group.Mask)
                                        .ToArray();

                            if (cells.Any()) {
                                int upper = 0;
                                int lower = 0;
                                int temp = group.Mask;

                                int value = 1;
                                while (temp > 0) {
                                    if ((temp & 1) > 0) {
                                        lower = upper;
                                        upper = value;
                                    }
                                    temp = temp >> 1;
                                    value += 1;
                                }

                                System.out.println(
                                        $
                                        "Values {lower} and {upper} in {group.Description} are in cells ({maskCells[0].Row + 1}, {maskCells[0].Column + 1}) and ({maskCells[1].Row + 1}, {maskCells[1].Column + 1}).");

                                foreach(var cell in cells)
                                {
                                    int maskToRemove = candidateMasks[cell.Index] & group.Mask;
                                    List<int> valuesToRemove = new List<int>();
                                    int curValue = 1;
                                    while (maskToRemove > 0) {
                                        if ((maskToRemove & 1) > 0) {
                                            valuesToRemove.Add(curValue);
                                        }
                                        maskToRemove = maskToRemove >> 1;
                                        curValue += 1;
                                    }

                                    String valuesReport = String.Join(", ", valuesToRemove.ToArray());
                                    System.out.println($"{valuesReport} cannot appear in ({cell.Row + 1}, {cell.Column + 1}).");

                                    candidateMasks[cell.Index] &= ~group.Mask;
                                    stepChangeMade = true;
                                }
                            }
                        }
                    }

                }

                // Try to find groups of digits of size N which only appear in N cells within row/column / block
                // When a set of N digits only appears in N cells within row/column/block, then no other digit can appear in the same set of cells
                // All other candidates can then be removed from those cells

                if (!changeMade && !stepChangeMade) {
                    IEnumerable<int> masks =
                            maskToOnesCount
                                    .Where(tuple = > tuple.Value > 1)
                                .Select(tuple = > tuple.Key).ToList();

                    var groupsWithNMasks =
                            masks
                                    .SelectMany(mask = >
                                    cellGroups
                                            .Where(group = > group.All(cell = > state[cell.Index] == 0
                                    || (mask & (1 << (state[cell.Index] - 1))) == 0))
                                        .Select(group = > new
                    {
                        Mask = mask,
                                Description = group.First().Description,
                                Cells = group,
                                CellsWithMask =
                                        group.Where(cell = > state[cell.Index] == 0 && (candidateMasks[cell.Index] & mask) != 0).ToList(),
                            CleanableCellsCount =
                                    group.Count(
                                            cell = > state[cell.Index] == 0 &&
                                            (candidateMasks[cell.Index] & mask) != 0 &&
                                            (candidateMasks[cell.Index] & ~mask) != 0)
                    }))
                                .Where(group = > group.CellsWithMask.Count() == maskToOnesCount[group.Mask])
                                .ToList();

                    foreach(var groupWithNMasks in groupsWithNMasks)
                    {
                        int mask = groupWithNMasks.Mask;

                        if (groupWithNMasks.Cells
                                .Any(cell = >
                                (candidateMasks[cell.Index] & mask) != 0 &&
                                (candidateMasks[cell.Index] & ~mask) != 0))
                        {
                            StringBuilder message = new StringBuilder();
                            message.Append($"In {groupWithNMasks.Description} values ");

                            String separator = String.Empty;
                            int temp = mask;
                            int curValue = 1;
                            while (temp > 0) {
                                if ((temp & 1) > 0) {
                                    message.Append($"{separator}{curValue}");
                                    separator = ", ";
                                }
                                temp = temp >> 1;
                                curValue += 1;
                            }

                            message.Append(" appear only in cells");
                            foreach(var cell in groupWithNMasks.CellsWithMask)
                            {
                                message.Append($" ({cell.Row + 1}, {cell.Column + 1})");
                            }

                            message.Append(" and other values cannot appear in those cells.");

                            System.out.println(message.ToString());
                        }

                        foreach(var cell in groupWithNMasks.CellsWithMask)
                        {
                            int maskToClear = candidateMasks[cell.Index] & ~groupWithNMasks.Mask;
                            if (maskToClear == 0) {
                                continue;
                            }

                            candidateMasks[cell.Index] &= groupWithNMasks.Mask;
                            stepChangeMade = true;

                            int valueToClear = 1;

                            String separator = String.Empty;
                            StringBuilder message = new StringBuilder();

                            while (maskToClear > 0) {
                                if ((maskToClear & 1) > 0) {
                                    message.Append($"{separator}{valueToClear}");
                                    separator = ", ";
                                }
                                maskToClear = maskToClear >> 1;
                                valueToClear += 1;
                            }

                            message.Append($" cannot appear in cell ({cell.Row + 1}, {cell.Column + 1}).");
                            System.out.println(message.ToString());
                        }
                    }
                }
            }

            // Final attempt - look if the board has multiple solutions
            if (!changeMade) {
                // This is the last chance to do something in this iteration:
                // If this attempt fails, board will not be entirely solved.

                // Try to see if there are pairs of values that can be exchanged arbitrarily
                // This happens when board has more than one valid solution

                Queue<Integer> candidateIndex1 = new ArrayDeque<>();
                Queue<Integer> candidateIndex2 = new ArrayDeque<Integer>();
                Queue<Integer> candidateDigit1 = new ArrayDeque<Integer>();
                Queue<Integer> candidateDigit2 = new ArrayDeque<Integer>();

                for (int i = 0; i < candidateMasks.length - 1; i++) {
                    if (maskToOnesCount.get(candidateMasks[i]) == 2) {
                        int row = i / 9;
                        int col = i % 9;
                        int blockIndex = 3 * (row / 3) + col / 3;

                        int temp = candidateMasks[i];
                        int lower = 0;
                        int upper = 0;
                        for (int digit = 1; temp > 0; digit++) {
                            if ((temp & 1) != 0) {
                                lower = upper;
                                upper = digit;
                            }
                            temp = temp >> 1;
                        }

                        for (int j = i + 1; j < candidateMasks.length; j++) {
                            if (candidateMasks[j] == candidateMasks[i]) {
                                int row1 = j / 9;
                                int col1 = j % 9;
                                int blockIndex1 = 3 * (row1 / 3) + col1 / 3;

                                if (row == row1 || col == col1 || blockIndex == blockIndex1) {
                                    candidateIndex1.add(i);
                                    candidateIndex2.add(j);
                                    candidateDigit1.add(lower);
                                    candidateDigit2.add(upper);
                                }
                            }
                        }
                    }
                }

                // At this point we have the lists with pairs of cells that might pick one of two digits each
                // Now we have to check whether that is really true - does the board have two solutions?

                List<Integer> stateIndex1 = new List<Integer>();
                List<Integer> stateIndex2 = new List<Integer>();
                List<Integer> value1 = new List<Integer>();
                List<Integer> value2 = new List<Integer>();

                while (candidateIndex1.Any()) {
                    int index1 = candidateIndex1.add();
                    int index2 = candidateIndex2.add();
                    int digit1 = candidateDigit1.add();
                    int digit2 = candidateDigit2.add();

                    int[] alternateState = new int[finalState.length];
                    //Array.Copy(state, alternateState, alternateState.length);
                    System.arraycopy(state, 0, alternateState, 0, alternateState.length);

                    if (finalState[index1] == digit1) {
                        alternateState[index1] = digit2;
                        alternateState[index2] = digit1;
                    } else {
                        alternateState[index1] = digit1;
                        alternateState[index2] = digit2;
                    }

                    // What follows below is a complete copy-paste of the solver which appears at the beginning of this method
                    // However, the algorithm couldn't be applied directly and it had to be modified.
                    // Implementation below assumes that the board might not have a solution.
                    stateStack = new Stack<int[]>();
                    rowIndexStack = new Stack<int>();
                    colIndexStack = new Stack<int>();
                    usedDigitsStack = new Stack<Boolean[]>();
                    lastDigitStack = new Stack<int>();

                    command = "expand";
                    while (command != "complete" && command != "fail") {
                        if (command == "expand") {
                            int[] currentState = new int[9 * 9];

                            if (stateStack.Any()) {
                                Array.Copy(stateStack.Peek(), currentState, currentState.length);
                            } else {
                                Array.Copy(alternateState, currentState, currentState.length);
                            }

                            int bestRow = -1;
                            int bestCol = -1;
                            Boolean[] bestUsedDigits = null;
                            int bestCandidatesCount = -1;
                            int bestRandomValue = -1;
                            Boolean containsUnsolvableCells = false;

                            for (int index = 0; index < currentState.length; index++) {
                                if (currentState[index] == 0) {

                                    int row = index / 9;
                                    int col = index % 9;
                                    int blockRow = row / 3;
                                    int blockCol = col / 3;

                                    Boolean[] isDigitUsed = new Boolean[9];

                                    for (int i = 0; i < 9; i++) {
                                        int rowDigit = currentState[9 * i + col];
                                        if (rowDigit > 0) {
                                            isDigitUsed[rowDigit - 1] = true;
                                        }

                                        int colDigit = currentState[9 * row + i];
                                        if (colDigit > 0) {
                                            isDigitUsed[colDigit - 1] = true;
                                        }

                                        int blockDigit = currentState[(blockRow * 3 + i / 3) * 9 + (blockCol * 3 + i % 3)];
                                        if (blockDigit > 0) {
                                            isDigitUsed[blockDigit - 1] = true;
                                        }
                                    } // for (i = 0..8)

                                    int candidatesCount = isDigitUsed.Where(used = > !used).Count();

                                    if (candidatesCount == 0) {
                                        containsUnsolvableCells = true;
                                        break;
                                    }

                                    int randomValue = rng.nextInt();

                                    if (bestCandidatesCount < 0 ||
                                            candidatesCount < bestCandidatesCount ||
                                            (candidatesCount == bestCandidatesCount && randomValue < bestRandomValue)) {
                                        bestRow = row;
                                        bestCol = col;
                                        bestUsedDigits = isDigitUsed;
                                        bestCandidatesCount = candidatesCount;
                                        bestRandomValue = randomValue;
                                    }
                                } // for (index = 0..81)
                            }

                            if (!containsUnsolvableCells) {
                                stateStack.push(currentState);
                                rowIndexStack.push(bestRow);
                                colIndexStack.push(bestCol);
                                usedDigitsStack.push(bestUsedDigits);
                                lastDigitStack.push(0); // No digit was tried at this position
                            }

                            // Always try to move after expand
                            command = "move";
                        } // if (command == "expand")
                        else if (command == "collapse") {
                            stateStack.pop();
                            rowIndexStack.pop();
                            colIndexStack.pop();
                            usedDigitsStack.pop();
                            lastDigitStack.pop();

                            if (stateStack.Any()) {
                                command = "move"; // Always try to move after collapse
                            } else {
                                command = "fail";
                            }
                        } else if (command == "move") {

                            int rowToMove = rowIndexStack.peek();
                            int colToMove = colIndexStack.peek();
                            int digitToMove = lastDigitStack.pop();

                            int rowToWrite = rowToMove + rowToMove / 3 + 1;
                            int colToWrite = colToMove + colToMove / 3 + 1;

                            Boolean[] usedDigits = usedDigitsStack.peek();
                            int[] currentState = stateStack.peek();
                            int currentStateIndex = 9 * rowToMove + colToMove;

                            int movedToDigit = digitToMove + 1;
                            while (movedToDigit <= 9 && usedDigits[movedToDigit - 1]) {
                                movedToDigit += 1;
                            }

                            if (digitToMove > 0) {
                                usedDigits[digitToMove - 1] = false;
                                currentState[currentStateIndex] = 0;
                                board[rowToWrite][colToWrite] = '.';
                            }

                            if (movedToDigit <= 9) {
                                lastDigitStack.push(movedToDigit);
                                usedDigits[movedToDigit - 1] = true;
                                currentState[currentStateIndex] = movedToDigit;
                                board[rowToWrite][colToWrite] = (char) ('0' + movedToDigit);

                                if (currentState.Any(digit = > digit == 0))
                                command = "expand";
                                    else
                                command = "complete";
                            } else {
                                // No viable candidate was found at current position - pop it in the next iteration
                                lastDigitStack.push(0);
                                command = "collapse";
                            }
                        } // if (command == "move")
                    } // while (command != "complete" && command != "fail")

                    if (command == "complete") {   // Board was solved successfully even with two digits swapped
                        stateIndex1.add(index1);
                        stateIndex2.add(index2);
                        value1.add(digit1);
                        value2.add(digit2);
                    }
                } // while (candidateIndex1.Any())

                if (stateIndex1.Any()) {
                    int pos = rng.nextInt(stateIndex1.size());
                    int index1 = stateIndex1.get(pos);
                    int index2 = stateIndex2.get(pos);
                    int digit1 = value1.get(pos);
                    int digit2 = value2.get(pos);
                    int row1 = index1 / 9;
                    int col1 = index1 % 9;
                    int row2 = index2 / 9;
                    int col2 = index2 % 9;

                    String description = "";

                    if (index1 / 9 == index2 / 9) {
                        description = $ "row #{index1 / 9 + 1}";
                    } else if (index1 % 9 == index2 % 9) {
                        description = $ "column #{index1 % 9 + 1}";
                    } else {
                        description = $ "block ({row1 / 3 + 1}, {col1 / 3 + 1})";
                    }

                    state[index1] = finalState[index1];
                    state[index2] = finalState[index2];
                    candidateMasks[index1] = 0;
                    candidateMasks[index2] = 0;
                    changeMade = true;

                    for (int i = 0; i < state.length; i++) {
                        int tempRow = i / 9;
                        int tempCol = i % 9;
                        int rowToWrite = tempRow + tempRow / 3 + 1;
                        int colToWrite = tempCol + tempCol / 3 + 1;

                        board[rowToWrite][colToWrite] = '.';
                        if (state[i] > 0) {
                            board[rowToWrite][colToWrite] = (char) ('0' + state[i]);
                        }
                    }

                    System.out.println(
                            "Guessing that "
                                    + digit1
                                    + " and "
                                    + digit2
                                    + " are arbitrary in "
                                    + description
                                    + " (multiple solutions): Pick "
                                    + finalState[index1]
                                    + "}->("
                                    + row1
                                    + 1
                                    + ", "
                                    + col1
                                    + 1
                                    + "), "
                                    + finalState[index2]
                                    + "->("
                                    + row2
                                    + 1
                                    + ", "
                                    + col2
                                    + 1
                                    + ").");
                }
            }

            if (changeMade) {
                // Print the board as it looks after one change was made to it
                System.out.println(String.Join(Environment.NewLine, board.Select(s = > new String(s)).ToArray()));
                String code =
                        String.Join(String.Empty, board.Select(s = > new String(s)).ToArray())
                            .Replace("-", String.Empty)
                        .Replace("+", String.Empty)
                        .Replace("|", String.Empty)
                        .Replace(".", "0");

                System.out.println("Code: {0}", code);
                System.out.println();
            } */
                }
            }
        }
    }

    public static void main(String[] args) {
        Play();
    }
}
