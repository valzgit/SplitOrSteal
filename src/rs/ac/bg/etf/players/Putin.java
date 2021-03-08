package rs.ac.bg.etf.players;

import java.util.ArrayList;

public class Putin extends Player {

	private Move myMove;
	private Move lastOppMove;
	// dodato static
	private static int brojpoteza = -1;
	private double skrtica = 0;
	private static int mene = 0;
	private double prevariome = 0;
	private boolean milosrdan = true;
	private static double normalnoigrali = 0;
	private static double cudnoigrali = 0;
	private int MojId;
	private static ArrayList<Boolean> decibate = new ArrayList<Boolean>();

	public Putin() {
		this.myMove = Move.PUT2COINS;
		this.lastOppMove = null;
		MojId = mene;
		mene++;

	}

	private static boolean rollCall(Player me) {
		// ima li potencijalno mrtvih
		boolean pronasao = false;

		for (int i = 0; i < decibate.size(); i++) {
			if (i != ((Putin) me).MojId && decibate.get(i) == true)
				pronasao = true;
		}
		return pronasao;
		// kakvi su bili igraci protiv prvog kojot-mentora

	}

	private static void hej(Player me) {
		if (decibate.size() != mene) {
			decibate = new ArrayList<Boolean>();
			for (int i = 0; i < mene; i++)
				decibate.add(false);
		}
		decibate.set(((Putin) (me)).getMojId(), true);
	}

	private static void poyy(Player me) {
		for (int i = 0; i < decibate.size(); i++) {
			decibate.set(i, false);
		}
	}

	public int getMojId() {
		return MojId;
	}

	@Override
	public void resetPlayerState() {
		brojpoteza = this.opponentMoves.size();
		markHistory();
		super.resetPlayerState();
		skrtica = 0;
		prevariome = 0;
		milosrdan = true;
		this.myMove = Move.PUT2COINS;
		this.lastOppMove = null;
		poyy(this);
	}

	private boolean checkHistory() {
		if ((normalnoigrali + cudnoigrali < 10)
				|| ((cudnoigrali != 0) && ((normalnoigrali / (normalnoigrali + cudnoigrali)) >= 0.75))
				|| (cudnoigrali == 0))
			return true;
		else
			return false;
	}

	private void markHistory() {
		double j = 0;
		double d = 0;
		double t = 0;
		for (int i = 0; i < this.opponentMoves.size(); i++) {
			if (this.opponentMoves.get(i) == Move.DONTPUTCOINS) {
				j = j + 1;
			} else if (this.opponentMoves.get(i) == Move.PUT1COIN) {
				d = d + 1;
			} else {
				t = t + 1;
			}
		}
		if (j + d + t > 5) {
			if ((j > (d + t)) || (d > (j + t)) || (t > (j + d))) {
				normalnoigrali += 1;
			} else {
				cudnoigrali += 1;
			}

		} else {
			normalnoigrali += 1;
		}

		// System.out.printf("Normalno igrali = %f , cudno = %f
		// \n",normalnoigrali,cudnoigrali);
	}

	private void antivirusmali() {
		int vel = this.opponentMoves.size();
		if (vel >= 4) {
			if (this.opponentMoves.get(vel - 1) == Move.PUT1COIN && this.opponentMoves.get(vel - 2) == Move.PUT2COINS
					&& this.opponentMoves.get(vel - 3) == Move.PUT1COIN) {
				skrtica++;
			} else if (this.opponentMoves.get(vel - 1) == Move.DONTPUTCOINS
					&& this.opponentMoves.get(vel - 2) == Move.PUT2COINS
					&& this.opponentMoves.get(vel - 3) == Move.DONTPUTCOINS) {
				skrtica++;
			}
		}

	}

	private void antivirusveliki() {

		int vel = this.opponentMoves.size();
		if (vel >= 4) {
			if (this.opponentMoves.get(vel - 1) == Move.DONTPUTCOINS && this.opponentMoves.get(vel - 2) == Move.PUT1COIN
					&& this.opponentMoves.get(vel - 3) == Move.DONTPUTCOINS) {
				prevariome++;
			}

		}

	}

	@Override
	public Move getNextMove() {
		hej(this);
		milosrdan = checkHistory();
		// odavde igram
		if (milosrdan == false) {
			if (rollCall(this) == true) {
				this.myMove = Move.PUT2COINS;
				return this.myMove;
			} else if (this.opponentMoves.size() == (brojpoteza - 1)) {
				this.myMove = Move.DONTPUTCOINS;
			} else {
				if (this.opponentMoves.size() > 0) {
					lastOppMove = this.opponentMoves.get(this.opponentMoves.size() - 1);
					if (lastOppMove == Move.DONTPUTCOINS) {
						if (this.opponentMoves.size() == 1) {
							prevariome = 2;
							skrtica += 0.5;
						} else {
							prevariome += 1;
							skrtica += 0.5;
						}
					} else if (lastOppMove == Move.PUT1COIN) {
						skrtica += 1;
						if (prevariome > 0)
							prevariome -= 0.5;

					} else if (lastOppMove == Move.PUT2COINS) {
						boolean bioskrtica = false;
						boolean biolopov = false;
						if (skrtica > 0) {
							skrtica -= 0.5;
							if (skrtica < 0)
								skrtica = 0;
							bioskrtica = true;
						}
						if (prevariome > 0) {
							if (bioskrtica == false)
								prevariome -= 1;
							else
								prevariome -= 0.5;
							if (prevariome < 0)
								prevariome = 0;
							biolopov = true;
						}
						if (biolopov == false && bioskrtica == true) {
							skrtica -= 0.5;
						}
						bioskrtica = false;
						biolopov = false;
					}
				}
				antivirusmali();
				if (prevariome >= 1.5 || ((prevariome + skrtica >= 3.5) && prevariome > 0)) {
					this.myMove = Move.DONTPUTCOINS;
				} else if (skrtica > 0) {
					if (((skrtica == 1 || skrtica == 0.5) && prevariome == 0)) {
						this.myMove = Move.PUT2COINS;
					} else {
						this.myMove = Move.PUT1COIN;
						// this.myMove = Move.DONTPUTCOINS;
					}

				} else {// ako nemam posla sa zlotvorima
					this.myMove = Move.PUT2COINS;
					if (prevariome == 1 || prevariome == 0.5) {
						this.myMove = Move.PUT1COIN;
					}
				}
			}
		} else {// ako jeste milosrdan
			if (rollCall(this) == true) {
				this.myMove = Move.PUT2COINS;
				return this.myMove;
			}

			else if (this.opponentMoves.size() == (brojpoteza - 1)) {
				if (rollCall(this) == true)
					this.myMove = Move.PUT2COINS;
				else
					this.myMove = Move.DONTPUTCOINS;
			} else {
				if (this.opponentMoves.size() > 0) {
					lastOppMove = this.opponentMoves.get(this.opponentMoves.size() - 1);
					if (lastOppMove == Move.DONTPUTCOINS) {
						if (this.opponentMoves.size() == 1) {
							prevariome = 2;
						} else {
							prevariome += 1;

						}
					} else if (lastOppMove == Move.PUT1COIN) {
						skrtica += 1;
						if (prevariome > 0)
							prevariome -= 1;

					} else if (lastOppMove == Move.PUT2COINS) {
						if (skrtica > 0)
							skrtica -= 1;
						if (prevariome > 0)
							prevariome -= 1;
					}
				}
				antivirusmali();
				antivirusveliki();
				if (prevariome >= 2) {
					this.myMove = Move.DONTPUTCOINS;
				} else if (skrtica > 0) {
					if (skrtica == 1 && prevariome == 0) {
						this.myMove = Move.PUT2COINS;
					} else {
						this.myMove = Move.PUT1COIN;
						// this.myMove = Move.DONTPUTCOINS;
					}

				} else {// ako nemam posla sa zlotvorima
					this.myMove = Move.PUT2COINS;
					if (prevariome == 1) {
						this.myMove = Move.PUT1COIN;
					}
				}
			}
		}
		return this.myMove;
	}
}
