package uk.ivanc.archimvvm.MVVM.View;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import uk.ivanc.archimvvm.R;
import uk.ivanc.archimvvm.RepositoryAdapter;
import uk.ivanc.archimvvm.databinding.MainActivityBinding;
import uk.ivanc.archimvvm.MVVM.Model.Repository;
import uk.ivanc.archimvvm.MVVM.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {
    private MainActivityBinding mBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mMainViewModel = new MainViewModel(this, this);
        mBinding.setViewModel(mMainViewModel);
        setSupportActionBar(mBinding.toolbar);
        setupRecyclerView(mBinding.reposRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainViewModel.destroy();
    }

    @Override
    public void onRepositoriesChanged(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) mBinding.reposRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
        hideSoftKeyboard();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.editTextUsername.getWindowToken(), 0);
    }
}
