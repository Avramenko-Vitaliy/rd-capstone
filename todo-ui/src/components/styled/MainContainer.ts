import { styled } from '@mui/material/styles';
import BorderedGrid from '@/components/styled/BorderedGrid';

type SpacingProp = number | string;

interface MainContainerProps {
  p?: SpacingProp;
  padding?: SpacingProp;
  borderRadius?: number | string;
}

const MainContainer = styled(BorderedGrid, {
  shouldForwardProp: (prop) => prop !== 'p' && prop !== 'padding' && prop !== 'borderRadius',
})<MainContainerProps>(({ theme, p, padding, borderRadius }) => {
  const defaultPadding: SpacingProp = '20px';
  const paddingValue = p ?? padding ?? defaultPadding;

  const defaultRadius: SpacingProp = '16px';
  const borderRadiusValue = borderRadius ?? defaultRadius;
  return {
    padding: typeof paddingValue === 'number' ? theme.spacing(paddingValue) : paddingValue,
    borderRadius: typeof borderRadiusValue === 'number' ? theme.spacing(borderRadiusValue) :  borderRadiusValue ,
  };
});

export default MainContainer;
