import Grid from '@mui/material/Grid';
import { styled } from '@mui/material/styles';
import { grey } from '@mui/material/colors';

const BorderedGrid = styled(Grid)(() => ({
  padding: '8px 12px',
  borderRadius: 8,
  backgroundColor: '#FFF',
  border: `1px solid ${grey[300]}`,
}));

export default BorderedGrid;
