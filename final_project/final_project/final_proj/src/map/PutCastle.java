package map;

import java.util.Vector;

public class PutCastle {


	private int castlenumber;

	public PutCastle(int castlenumber ) {
		// TODO Auto-generated constructor stub
		this.castlenumber = castlenumber;
		
	}

	public Vector<int[]> putCastle(int[][] castlearr) {
		Vector<int[]> v = new Vector<int[]>();
		switch (castlenumber) {
		case 2:
			if (castlearr.length < 2) {
				return v;
			}
			double disp = 0;
			int[] p1 = new int[2];
			int[] p2 = new int[2];
			p1[0] = castlearr[0][0];
			p1[1] = castlearr[0][1];
			p2[0] = castlearr[1][0];
			p2[1] = castlearr[1][1];

			for (int t = 0; t < castlearr.length; t++) {
				for (int q = t; q < castlearr.length; q++) {
					if (disp < Math.sqrt(Math.pow(castlearr[t][0] - castlearr[q][0], 2)
							+ Math.pow(castlearr[t][1] - castlearr[q][1], 2))) {
						disp = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[q][0], 2)
								+ Math.pow(castlearr[t][1] - castlearr[q][1], 2));
						p1[0] = castlearr[t][0];
						p1[1] = castlearr[t][1];
						p2[0] = castlearr[q][0];
						p2[1] = castlearr[q][1];
					}
				}
			}

			v.addElement(p1);
			v.addElement(p2);

			// for show where is casteles
			// for(int i=0;i<v.size();i++){
			// System.out.println(v.elementAt(i)[0]+" "+v.elementAt(i)[1]);
			// }
			
			break;
		case 3:
			if (castlearr.length < 3) {
				return v;
			}
			double middis3 = 0;
			int[] pp1 = new int[2];
			int[] pp2 = new int[2];
			int[] pp3 = new int[2];
			pp1[0] = castlearr[0][0];
			pp1[1] = castlearr[0][1];
			pp2[0] = castlearr[1][0];
			pp2[1] = castlearr[1][1];
			pp3[0] = castlearr[2][0];
			pp3[1] = castlearr[2][1];

			for (int t = 0; t < castlearr.length; t++) {
				for (int q = t; q < castlearr.length; q++) {
					for (int w = q; w < castlearr.length; w++) {
						double dis1 = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[q][0], 2)
								+ Math.pow(castlearr[t][1] - castlearr[q][1], 2));
						double dis2 = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[w][0], 2)
								+ Math.pow(castlearr[t][1] - castlearr[w][1], 2));
						double dis3 = Math.sqrt(Math.pow(castlearr[q][0] - castlearr[w][0], 2)
								+ Math.pow(castlearr[q][1] - castlearr[w][1], 2));
						if (middis3 < ((dis1 + dis2 + dis3) / 3)) {
							middis3 = (dis1 + dis2 + dis3) / 3;
							pp1[0] = castlearr[t][0];
							pp1[1] = castlearr[t][1];
							pp2[0] = castlearr[q][0];
							pp2[1] = castlearr[q][1];
							pp3[0] = castlearr[w][0];
							pp3[1] = castlearr[w][1];
						}

					}
				}
			}
			v.addElement(pp1);
			v.addElement(pp2);
			v.addElement(pp3);

//			for show where is casteles
//			for (int i = 0; i < v.size(); i++) {
//				System.out.println(v.elementAt(i)[0] + " " + v.elementAt(i)[1]);
//			}
			
			break;
		case 4:
			if (castlearr.length < 4) {
				return v;
			}
			double middis4 = 0;
			int[] ppp1 = new int[2];
			int[] ppp2 = new int[2];
			int[] ppp3 = new int[2];
			int[] ppp4 = new int[2];
			ppp1[0] = castlearr[0][0];
			ppp1[1] = castlearr[0][1];
			ppp2[0] = castlearr[1][0];
			ppp2[1] = castlearr[1][1];
			ppp3[0] = castlearr[2][0];
			ppp3[1] = castlearr[2][1];
			ppp4[0] = castlearr[3][0];
			ppp4[1] = castlearr[3][1];
			if(castlearr.length>100){
					for (int q = 1; q < castlearr.length; q++) {
						for (int w = q; w < castlearr.length; w++) {
							for(int f=w;f<castlearr.length;f++){
								double dis1 = Math.sqrt(Math.pow(castlearr[0][0] - castlearr[q][0], 2) + Math.pow(castlearr[0][1] - castlearr[q][1], 2));
								double dis2 = Math.sqrt(Math.pow(castlearr[0][0] - castlearr[w][0], 2) + Math.pow(castlearr[0][1] - castlearr[w][1], 2));
								double dis3 = Math.sqrt(Math.pow(castlearr[0][0] - castlearr[f][0], 2) + Math.pow(castlearr[0][1] - castlearr[f][1], 2));
								double dis4 = Math.sqrt(Math.pow(castlearr[q][0] - castlearr[w][0], 2) + Math.pow(castlearr[q][1] - castlearr[w][1], 2));
								double dis5 = Math.sqrt(Math.pow(castlearr[q][0] - castlearr[f][0], 2) + Math.pow(castlearr[q][1] - castlearr[f][1], 2));
								double dis6 = Math.sqrt(Math.pow(castlearr[w][0] - castlearr[f][0], 2) + Math.pow(castlearr[w][1] - castlearr[f][1], 2));
								if (middis4 < ((dis1 + dis2 + dis3 + dis4 + dis5 + dis6) / 6)) {
									middis4 = (dis1 + dis2 + dis3 + dis4 + dis5 + dis6) / 6;
									ppp2[0] = castlearr[q][0];
									ppp2[1] = castlearr[q][1];
									ppp3[0] = castlearr[w][0];
									ppp3[1] = castlearr[w][1];
									ppp4[0] = castlearr[f][0];
									ppp4[1] = castlearr[f][1];
								}
							}

						}
					}
				
				
			}else{

			for (int t = 0; t < castlearr.length; t++) {
				for (int q = t; q < castlearr.length; q++) {
					for (int w = q; w < castlearr.length; w++) {
						for(int f=w;f<castlearr.length;f++){
							double dis1 = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[q][0], 2) + Math.pow(castlearr[t][1] - castlearr[q][1], 2));
							double dis2 = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[w][0], 2) + Math.pow(castlearr[t][1] - castlearr[w][1], 2));
							double dis3 = Math.sqrt(Math.pow(castlearr[t][0] - castlearr[f][0], 2) + Math.pow(castlearr[t][1] - castlearr[f][1], 2));
							double dis4 = Math.sqrt(Math.pow(castlearr[q][0] - castlearr[w][0], 2) + Math.pow(castlearr[q][1] - castlearr[w][1], 2));
							double dis5 = Math.sqrt(Math.pow(castlearr[q][0] - castlearr[f][0], 2) + Math.pow(castlearr[q][1] - castlearr[f][1], 2));
							double dis6 = Math.sqrt(Math.pow(castlearr[w][0] - castlearr[f][0], 2) + Math.pow(castlearr[w][1] - castlearr[f][1], 2));
							if (middis4 < ((dis1 + dis2 + dis3 + dis4 + dis5 + dis6) / 6)) {
								middis4 = (dis1 + dis2 + dis3 + dis4 + dis5 + dis6) / 6;
								ppp1[0] = castlearr[t][0];
								ppp1[1] = castlearr[t][1];
								ppp2[0] = castlearr[q][0];
								ppp2[1] = castlearr[q][1];
								ppp3[0] = castlearr[w][0];
								ppp3[1] = castlearr[w][1];
								ppp4[0] = castlearr[f][0];
								ppp4[1] = castlearr[f][1];
							}
						}

					}
				}
			}
			
			
			}
			v.addElement(ppp1);
			v.addElement(ppp2);
			v.addElement(ppp3);
			v.addElement(ppp4);
			
//			//for show where is casteles
//			for (int i = 0; i < v.size(); i++) {
//				System.out.println(v.elementAt(i)[0] + " " + v.elementAt(i)[1]);
//			}
			
			break;

		default:
			System.out.println("invalid num  for castel");
			break;
		}
		return v;

	}
}
