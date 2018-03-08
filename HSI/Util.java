package homework4;
class Util {
	final static int checkPixelBounds(int value){
		if (value >255) return 255;
		if (value <0) return 0;
		return value;
 	}
 
	//get red channel from colorspace (4 bytes)
	public final static int getR(int rgb){
		  return checkPixelBounds((rgb & 0x00ff0000)>>>16);	
    }

	//get green channel from colorspace (4 bytes)
	public final static int getG(int rgb){
	  return checkPixelBounds((rgb & 0x0000ff00)>>>8);
	}
	
	//get blue channel from colorspace (4 bytes)
	public final static int getB(int rgb){
		  return  checkPixelBounds(rgb & 0x000000ff);
	}
	
	public static double getH(double r,double g,double b){
		double H = 0;
		double max = Max(r, g, b);
		double min = Min(r, g, b);
		
		if(max == min){
			H = 0;
		}
		else if(max == r && g >= b){
			H = 60.0 * (double)( (g - b) / (max - min) ) + 0.0;
		}
		else if(max == r && g < b){
			H = 60.0 * (double)( (g - b) / (max - min) ) + 360.0;
		}
		else if(max == g){
			H = 60.0 * (double)( (b - r) / (max - min) ) + 120.0;
		}
		else if(max == b){
			H = 60.0 * (double)( (r - g) / (max - min) ) + 240.0;
		}
		return H;
	}
	
	public static double getS(double r,double g,double b){
		double max = Max(r, g, b);
		double min = Min(r, g, b);
		double S = 0.0;
		double I = getI(r, g , b);
		
		if(I == 0 || max == min){
			S = 0;
		}
		else if(I > 0 && I <= 0.5){
			S = (double)((max - min) / (max + min));
		}
		else if(I > 0.5){
			S = (double)((max - min) / (2.0 - (max + min)));
		}
		return S;
	}
	
	public static double getI(double r,double g,double b){
		double max = Max(r, g, b);
		double min = Min(r, g, b);
		double I = (double)((max + min) / 2.0);
		return I;
	}
	
	public static double getQ(double S, double I){
		double Q = 0;
		if( I < 0.5){
			Q = I * (1.0 + S);
		}
		else{
			Q = I + S - (I * S);
		}
		return Q;
	}
	
	public static double getP(double S, double I){
		return (double)(2 * I - getQ(S,I));
	}
	
	public static double Color(double tC,double S,double I){
		double C = 0.0;
		double p = getP(S,I);
		double q = getQ(S,I);
		
		if(tC < (double)1.0/6.0 && tC > 0.0){
			C = p + ((q - p) * 6.0 * tC);
		}
		else if(tC < 0.5 && tC >= (double)(1.0/6.0)){
			C = q;
		}
		else if(tC <(double)(2.0/3.0) && tC >= 0.5){
			C = p + ((q - p) * 6.0 * ((double)(2.0/3.0) - tC));
		}
		else{
			C = p;
		}
		return C;
	}
	
	public static double R(double H,double S,double I){
		double tR = (double)(H/360.0) + (double)(1.0/3.0); 
		return Color(tR,S,I);
	}
	
	public static double G(double H,double S,double I){
		double tG = (double)(H / 360.0);
		return Color(tG,S,I);
	}
	
	public static double B(double H,double S,double I){
		double tB = ((double)(H / 360.0) - (double)(1.0/3.0));
		return Color(tB,S,I);
	}
	
	public static double Max(double r,double g,double b){ 
		double max = r;
        
        if (b > max) {
            max = b;
        }
        if (g > max) {
            max = g;
        }
		return max;
	}
	
	public static double Min(double r,double g,double b){
		double min = r;
		
		if (b < min) {
            min = b;
        }
        if (g < min) {
            min = g;
        }
		return min;
	}
}