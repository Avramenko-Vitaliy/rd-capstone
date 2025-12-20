import { Navigate, Route, Routes } from 'react-router';
import { Spinner } from '@/features/spinner';
import { SnackbarMessages } from '@/features/messages';
import Header from '@/layout/Header';
import TodosPage from '@/pages/TodosPage';
import Main from '@/components/styled/Main';

function App() {
  return (
    <>
      <Spinner />
      <SnackbarMessages />
      <Header />
      <Main>
        <Routes>
          <Route path="/todos" element={<TodosPage />} />
          <Route path="*" element={<Navigate to="/todos" />} />
        </Routes>
      </Main>
    </>
  );
}

export default App;
