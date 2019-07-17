PFont font;
int hour, minute, seconds, prevMin;
String meridiano = "AM", zeroHour = "0", zeroMinute = "0";

void setup() {
  size(600, 400);
  font = createFont("Megrim", 50);
  textFont(font);
  stroke(0);
  prevMin = -1;
}

void draw() {
  background(255);
  
  hour = hour();
  minute = minute();
  seconds = second();
  
  if(hour > 12) {
    hour -= 12;
    meridiano = "PM";
  } else{
    if(hour == 0) {
      hour = 12;
    }
    
    if(hour < 10) {
      zeroHour = "0";
    } else{
      zeroHour = "";
    }
    meridiano = "AM";
  }
  
  if(minute < 10) {
    zeroMinute = "0";
  } else{
    zeroMinute = "";
  }
  
  if(prevMin != minute && frameCount != 1) {
    stroke(random(0, 255), random(0, 255), random(0, 255));
  }
  
  fill(0);
  text(zeroHour + hour + " : " + zeroMinute + minute + " " + meridiano, 185, 220);
  
  float endAngle = map(seconds, 0, 60, -PI/2, 3 * PI/2);  //angle must be in radians, remember that on this coordinates angles are (+) clockwise
  
  noFill();
  strokeWeight(4);
  arc(width/2, height/2, 350, 350, -PI/2, endAngle);      //just draw the border of an ellipse, from - PI/2(start angle) to the actual calculadted angle
  prevMin = minute;
}
