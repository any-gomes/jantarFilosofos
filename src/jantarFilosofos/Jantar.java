package jantarFilosofos;

class Filosofos extends Thread {
	private Object hashiD;
	private Object hashiE;
	

	public Filosofos(Object hashiE, Object hashiD) {
		this.hashiE = hashiE;
		this.hashiD = hashiD;
	}

	private void fazer(String acao) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + acao);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	@Override
	public void run() {
		try {
			while (true) {
				fazer("Pensando...");
				synchronized (hashiE) {
					fazer("Pegou o hashi esquerdo!");
					synchronized (hashiD) {
						fazer("Pegou o hashi direito, e comeu!");
						fazer("Soltou o hashi direito!");
					}
					fazer("Soltou o hashi esquerdo!");
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}

class Jantar {
	public static void main(String[] args) throws Exception {
		final Filosofos[] filosofos = new Filosofos[5];
		Object[] hashis = new Object[filosofos.length];

		for (int i = 0; i < hashis.length; i++) {
			hashis[i] = new Object();
		}

		for (int i = 0; i < filosofos.length; i++) {
			Object hashiE = hashis[i];
			Object hashiD = hashis[(i + 1) % hashis.length];

			if (i == filosofos.length - 1) {
				filosofos[i] = new Filosofos(hashiD, hashiE);
			} else {
				filosofos[i] = new Filosofos(hashiE, hashiD);
			}
			Thread thread = new Thread(filosofos[i], "Filosofo " + (i + 1) + ": ");
			thread.start();
		}
	}
}

