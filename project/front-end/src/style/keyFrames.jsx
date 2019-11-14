import { keyframes } from 'styled-components';

/*******************
    Keyframe
********************/
export const ShakeWeakly = keyframes`
  0% {
    transform: translateX(0);
  }
  30% {
    transform: translateX(4px);
  }
  50% {
    transform: translateX(0);
  }
  70% {
    transform: translateX(4px);
  }
  100% {
    transform: translateX(0);
  }
`;