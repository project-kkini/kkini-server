package com.server.ggini.domain.member.domain.nickName;

import java.util.List;
import java.util.Random;

public enum Animal {
	돼지, 고양이, 강아지, 반달곰, 다람쥐, 토끼, 상어, 금붕어, 사자, 악어, 여우, 고래,
	표범, 수달, 사슴, 기린, 박쥐, 나비, 제비, 개미, 하마, 물개;

	private static final List<Animal> VALUES = List.of(values());
	private static final Random RANDOM = new Random();

	public static String getRandomName() {
		return VALUES.get(RANDOM.nextInt(VALUES.size())).name();
	}
}
