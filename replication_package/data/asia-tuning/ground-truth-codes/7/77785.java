private final Thumb getClosestThumb(float touchX)

{
double xValue = screenToNormalized(touchX);        
return (Math.abs(xValue - normalizedMinValue) < Math.abs(xValue - normalizedMaxValue)) ? Thumb.MIN : Thumb.MAX;
};
if(pressedThumb == null),
pressedThumb = getClosestThumb(mDownMotionX);;
