package net.sinamegapolis.trashcube.structure;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class StructureList {

    public static ArrayList<BlockPos> getCubeStructure1(BlockPos pos){
        ArrayList<BlockPos> structure = new ArrayList<>();
        structure = makePart1(structure, pos);
        structure = makePart2(structure, structure.get(5));
        structure = makePart3(structure, structure.get(12));
        structure = makePart4(structure, structure.get(25));
        structure = makePart5(structure, structure.get(41));
        return structure;
    }

    private static ArrayList<BlockPos> makePart1(ArrayList<BlockPos> Structure, BlockPos pos){
        pos = pos.west();
        Structure.add(pos);
        pos = pos.west();
        Structure.add(pos);

        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);

        pos = pos.up();
        Structure.add(pos);
        pos = pos.up();
        Structure.add(pos);

        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);
        return Structure;
    }

    private static ArrayList<BlockPos> makePart2(ArrayList<BlockPos> Structure, BlockPos pos){
        pos = pos.east();
        Structure.add(pos);
        BlockPos pos1 = pos.east();
        Structure.add(pos1);

        pos = pos1.down();
        Structure.add(pos);
        pos = pos1.down();
        Structure.add(pos);

        pos = pos1.east();
        Structure.add(pos);

        return Structure;
    }

    public static ArrayList<BlockPos> makePart3(ArrayList<BlockPos> Structure, BlockPos pos){
        BlockPos pos1= pos.east();
        Structure.add(pos1);

        pos = pos1.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);

        pos = pos1.down();
        Structure.add(pos);
        pos = pos.down();
        Structure.add(pos);

        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);

        pos = pos.up();
        Structure.add(pos);
        pos = pos.up();
        Structure.add(pos);
        pos = pos.up();
        Structure.add(pos);
        pos = pos.up();
        Structure.add(pos);

        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);

        return Structure;
    }

    public static ArrayList<BlockPos> makePart4(ArrayList<BlockPos> Structure, BlockPos pos){
        pos = pos.west();
        Structure.add(pos);
        pos = pos.west();
        Structure.add(pos);

        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);
        pos = pos.south();
        Structure.add(pos);

        pos = pos.west();
        Structure.add(pos);
        pos = pos.west();
        Structure.add(pos);

        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);
        pos = pos.north();
        Structure.add(pos);

        return Structure;
    }

    public static ArrayList<BlockPos> makePart5(ArrayList<BlockPos> Structure, BlockPos pos){
        pos = pos.down();
        Structure.add(pos);
        pos = pos.down();
        Structure.add(pos);
        pos = pos.down();
        Structure.add(pos);
        pos = pos.down();
        Structure.add(pos);

        pos = pos.east();
        Structure.add(pos);
        pos = pos.east();
        Structure.add(pos);

        return Structure;
    }
}
