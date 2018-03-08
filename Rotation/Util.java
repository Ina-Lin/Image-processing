package homework3;

class Util {
	final static int checkPixelBounds(int value){
		if (value >255) return 255;
		if (value <0) return 0;
		return value;
 	}
 
	//get red channel from colorspace (4 bytes)
	final static int getR(int rgb){
		  return checkPixelBounds((rgb & 0x00ff0000)>>>16);	
    }

	//get green channel from colorspace (4 bytes)
	final static int getG(int rgb){
	  return checkPixelBounds((rgb & 0x0000ff00)>>>8);
	}
	
	//get blue channel from colorspace (4 bytes)
	final static int getB(int rgb){
		  return  checkPixelBounds(rgb & 0x000000ff);
	}
	
	static int linear(int beginColor,int endColor,double rate){	
		return Util.checkPixelBounds((int) (beginColor+(endColor-beginColor)*rate));
	}

	static int bilinear(int a,int b,int c,int d,double x,double y){			
		int floorHorizontal = linear(a,b,y-Math.floor(y));
		int ceilHorizontal = linear(c,d,y-Math.floor(y));
		int middleVertical = linear(floorHorizontal,ceilHorizontal,x-Math.floor(x));
		return checkPixelBounds(middleVertical);		
	}
	
	static int adjust(int max,int n){
		if(n > max-1)
			return max-1;
		else if(n<0)
			return 0;
		else
			return n;
	}
	
}