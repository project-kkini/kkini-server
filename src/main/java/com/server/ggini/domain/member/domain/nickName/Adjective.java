package com.server.ggini.domain.member.domain.nickName;

import java.util.List;
import java.util.Random;

public enum Adjective {
	깐깐한, 열받은, 배부른, 배고픈, 친절한, 행복한, 즐거운, 졸린, 피곤한, 게으른, 슬픈,
	무서운, 착한, 느긋한, 용감한, 성실한, 명랑한, 조용한, 화난, 진지한, 우울한,
	신나는, 목마른, 차가운, 뜨거운, 시원한, 따뜻한, 깨끗한, 불안한, 활발한, 우직한,
	단순한, 복잡한, 유쾌한, 놀라운, 당당한, 솔직한, 겸손한, 부끄러운, 당황한,
	차분한, 민첩한, 투명한, 고요한, 온화한;

	private static final List<Adjective> VALUES = List.of(values());
	private static final Random RANDOM = new Random();

	public static String getRandomName() {
		return VALUES.get(RANDOM.nextInt(VALUES.size())).name();
	}
}
