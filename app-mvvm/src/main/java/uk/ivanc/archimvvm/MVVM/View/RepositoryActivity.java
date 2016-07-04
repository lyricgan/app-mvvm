package uk.ivanc.archimvvm.MVVM.View;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import uk.ivanc.archimvvm.CacheManager.Managers.ObjectTransferManager;
import uk.ivanc.archimvvm.R;
import uk.ivanc.archimvvm.Utils.KeyGenerator;
import uk.ivanc.archimvvm.databinding.RepositoryActivityBinding;
import uk.ivanc.archimvvm.MVVM.Model.Repository;
import uk.ivanc.archimvvm.MVVM.ViewModel.RepositoryViewModel;

public class RepositoryActivity extends AppCompatActivity {
    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";
    private RepositoryViewModel mRepositoryViewModel;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        String key = KeyGenerator.get();
        ObjectTransferManager.instance.push(key, repository);
        intent.putExtra(EXTRA_REPOSITORY, key);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RepositoryActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.repository_activity);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String key = getIntent().getStringExtra(EXTRA_REPOSITORY);
        Repository repository = (Repository) ObjectTransferManager.instance.pop_remove(key);
        mRepositoryViewModel = new RepositoryViewModel(this, repository);
        binding.setViewModel(mRepositoryViewModel);

        //Currently there is no way of setting an activity title using data binding
        setTitle(repository.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRepositoryViewModel.destroy();
    }
}
