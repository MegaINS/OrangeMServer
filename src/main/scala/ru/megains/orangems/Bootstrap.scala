package ru.megains.orangems

import ru.megains.orangem.block.{Block, BlockAir}
import ru.megains.orangem.item.{ItemMass, ItemPack, ItemSingle}
import ru.megains.orangem.register.{GameRegister, TGameRegister}
import ru.megains.orangem.utils.Logger

object Bootstrap extends Logger[OrangeMServer] {

    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks(GameRegister)
          //  log.info("Items init...")
          //  initItems(GameRegisterRender)
           // log.info("TileEntity init...")
          //  initTileEntity(GameRegister)
          //  log.info("Entity init...")
          //  initEntity(GameRegister)
        }

    }

    def initBlocks(gameRegister:TGameRegister): Unit = {


        gameRegister.registerBlock(0, BlockAir)

        gameRegister.registerBlock(1, new Block("stone"))

      //  gameRegister.registerBlock(2,block)

        gameRegister.registerBlock(2,new Block("grass"))
//        gameRegister.registerBlock(3, new BlockTest("test1",1))
//        gameRegister.registerBlock(4, new BlockTest("test2",2))
//        gameRegister.registerBlock(5, new BlockTest("test3",3))
//        gameRegister.registerBlock(6, new BlockTest("test4",4))
//        gameRegister.registerBlock(7, new BlockTest("test5",5))
//        gameRegister.registerBlock(8, new BlockTest("test6",6))
//        gameRegister.registerBlock(9, new BlockTest("test7",7))
//        gameRegister.registerBlock(10, new BlockTest("test8",8))
//        gameRegister.registerBlock(11, new BlockTest("test9",9))
//        gameRegister.registerBlock(12, new BlockTest("test10",10))
//        gameRegister.registerBlock(13, new BlockTest("test11",11))
//        gameRegister.registerBlock(14, new BlockTest("test12",12))
//        gameRegister.registerBlock(15, new BlockTest("test13",13))
//        gameRegister.registerBlock(16, new BlockTest("test14",14))
//        gameRegister.registerBlock(17, new BlockTest("test15",15))
//        gameRegister.registerBlock(18, new BlockTest("test16",16))
        gameRegister.registerBlock(19, new Block("rotateTexture"))

//        gameRegister.registerBlock(23, new BlockTest("test17",17))


//        gameRegister.registerBlock(6, new BlockStare("stare",0))
//        gameRegister.registerBlock(7, new BlockStare("stare1",1))
//
//        gameRegister.registerBlock(8,new BlockGrass("grass"))
//
//

//        gameRegister.registerBlock(18, new BlockGlass("glass"))
//        gameRegister.registerBlock(19, new BlockChest("chest"))
//        gameRegister.registerBlock(20, new BlockRotateTexture())

    }

    def initItems(gameRegister:TGameRegister): Unit = {
        gameRegister.registerItem(1000, new ItemPack("stick"))
        gameRegister.registerItem(1001, new ItemMass("coal"))
        gameRegister.registerItem(1002, new ItemSingle("helmet"))
    }

//    def initTileEntity(gameRegister:TGameRegister): Unit = {
//        gameRegister.registerTileEntity(0,classOf[TileEntityChest])
//       // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
//    }
//    def initEntity(gameRegister:TGameRegister): Unit = {
//        gameRegister.registerEntity(0,classOf[EntityItem])
//        gameRegister.registerEntity(1,classOf[EntityCube])
//    }
}
