package net.sinamegapolis.trashcube.structure;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Random;

public class StructureList {

    //TODO: add more structures
    //Returns a Random Structure from the list
    public static ArrayList<BlockPos> getRandomCubeStructure(BlockPos pos){
        int r = new Random().nextInt(4);
        switch(r){
            case(0):
                return getCubeStructure1(pos);
            case(1):
                return getCubeStructure2(pos);
            case(2):
                return getCubeStructure3(pos);
            case(3):
                return getCubeStructure4(pos);
        }
        return new ArrayList<>();
    }

    private static ArrayList<BlockPos> getCubeStructure1(BlockPos pos){
        ArrayList<BlockPos> structure = new ArrayList<>();
        makeCube1Part1(structure, pos);
        makeCube1Part2(structure, structure.get(5));
        makeCube1Part3(structure, structure.get(12));
        makeCube1Part4(structure, structure.get(25));
        makeCube1Part5(structure, structure.get(41));
        return structure;
    }

    private static ArrayList<BlockPos> getCubeStructure2(BlockPos pos){
        ArrayList<BlockPos> structure = new ArrayList<>();
        makeCube2Part1(structure, pos);
        makeCube2Part2(structure, structure.get(1));
        makeCube2Part3(structure, structure.get(19));
        makeCube2Part4(structure, structure.get(33));
        return structure;
    }

    private static ArrayList<BlockPos> getCubeStructure3(BlockPos pos){
        ArrayList<BlockPos> structure = new ArrayList<>();
        makeCube3Part1(structure, pos);
        makeCube3Part2(structure, structure.get(17));
        makeCube3Part3(structure, structure.get(33));
        return structure;
    }

    private static ArrayList<BlockPos> getCubeStructure4(BlockPos pos){
        ArrayList<BlockPos> structure = new ArrayList<>();
        makeCube4Part1(structure, pos);
        makeCube4Part2(structure, structure.get(9));
        makeCube4Part3(structure, structure.get(33));
        return structure;
    }

    private static ArrayList<BlockPos> makeCube4Part1(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube4Part2(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube4Part3(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube3Part1(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube3Part2(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube3Part3(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        BlockPos pos1 = pos.south();
        structure.add(pos1);

        pos = pos1.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);


        pos = pos1.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube2Part1(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.west();
        structure.add(pos);
        pos = pos.west();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        return structure;
    }

    private static ArrayList<BlockPos> makeCube2Part2(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube2Part3(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.east();
        structure.add(pos);
        pos = pos.east();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube2Part4(ArrayList<BlockPos> structure, BlockPos pos) {
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);

        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);
        pos = pos.south();
        structure.add(pos);

        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);
        pos = pos.down();
        structure.add(pos);

        pos = pos.north();
        structure.add(pos);
        pos = pos.north();
        structure.add(pos);

        pos = pos.up();
        structure.add(pos);
        pos = pos.up();
        structure.add(pos);
        return structure;
    }

    private static ArrayList<BlockPos> makeCube1Part1(ArrayList<BlockPos> Structure, BlockPos pos){
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

    private static ArrayList<BlockPos> makeCube1Part2(ArrayList<BlockPos> Structure, BlockPos pos){
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

    public static ArrayList<BlockPos> makeCube1Part3(ArrayList<BlockPos> Structure, BlockPos pos){
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

    public static ArrayList<BlockPos> makeCube1Part4(ArrayList<BlockPos> Structure, BlockPos pos){
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

    public static ArrayList<BlockPos> makeCube1Part5(ArrayList<BlockPos> Structure, BlockPos pos){
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
