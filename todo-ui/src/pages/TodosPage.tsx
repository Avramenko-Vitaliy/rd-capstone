import MainContainer from '@/components/styled/MainContainer';
import Grid from '@mui/material/Grid';
import Divider from '@mui/material/Divider';
import SearchFilter from '@/components/filters/SearchFilter';
import Todos from '@/features/todos/components/Todos';
import Button from '@mui/material/Button';

function TodosPage() {
  return (
    <MainContainer container size={12} spacing={2}>
      <Grid
        size={12}
        gap={1}
        display="flex"
        alignItems="center"
        justifyContent="flex-end"
      >
        <SearchFilter fieldName="search" />
        <Button variant="contained">
          Add ToDo
        </Button>
      </Grid>
      <Grid size={12}>
        <Divider />
      </Grid>
      <Todos />
    </MainContainer>
  );
}

export default TodosPage;
