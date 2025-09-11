package ru.snake.remote.core.tile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileCacheTest {

	@Test
	public void mustSaveAllValues() {
		TileCache cache = new TileCache(5);

		Assertions.assertEquals(0, cache.put(new byte[] { 0 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 1 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 2 }).getTileIndex());
		Assertions.assertEquals(3, cache.put(new byte[] { 3 }).getTileIndex());
		Assertions.assertEquals(4, cache.put(new byte[] { 4 }).getTileIndex());
	}

	@Test
	public void mustRemoveFirstValues() {
		TileCache cache = new TileCache(5);

		Assertions.assertEquals(0, cache.put(new byte[] { 0 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 1 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 2 }).getTileIndex());
		Assertions.assertEquals(3, cache.put(new byte[] { 3 }).getTileIndex());
		Assertions.assertEquals(4, cache.put(new byte[] { 4 }).getTileIndex());
		Assertions.assertEquals(0, cache.put(new byte[] { 5 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 6 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 7 }).getTileIndex());
		Assertions.assertEquals(3, cache.put(new byte[] { 8 }).getTileIndex());
		Assertions.assertEquals(4, cache.put(new byte[] { 9 }).getTileIndex());
		Assertions.assertEquals(0, cache.put(new byte[] { 10 }).getTileIndex());
	}

	@Test
	public void mustRemoveInLRUOrder() {
		TileCache cache = new TileCache(5);

		Assertions.assertEquals(0, cache.put(new byte[] { 0 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 1 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 2 }).getTileIndex());
		Assertions.assertEquals(0, cache.put(new byte[] { 0 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 1 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 2 }).getTileIndex());
		Assertions.assertEquals(3, cache.put(new byte[] { 3 }).getTileIndex());
		Assertions.assertEquals(4, cache.put(new byte[] { 4 }).getTileIndex());
		Assertions.assertEquals(0, cache.put(new byte[] { 0 }).getTileIndex());
		Assertions.assertEquals(1, cache.put(new byte[] { 5 }).getTileIndex());
		Assertions.assertEquals(2, cache.put(new byte[] { 6 }).getTileIndex());
		Assertions.assertEquals(3, cache.put(new byte[] { 7 }).getTileIndex());
	}

}
