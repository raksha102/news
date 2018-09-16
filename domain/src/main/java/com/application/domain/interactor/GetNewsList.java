package com.application.domain.interactor;

import com.application.domain.News;
import com.application.domain.executor.PostExecutionThread;
import com.application.domain.executor.ThreadExecutor;
import com.application.domain.repositoty.NewsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GetNewsList extends UseCase<Single<List<News>>, GetNewsList.Params> {

    private final NewsRepository mNewsRepository;

    @Inject
    public GetNewsList(NewsRepository newsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mNewsRepository = newsRepository;
    }

    @Override
    public Single<List<News>> execute(Params params) {
        return mNewsRepository.getNews(params.source, 0, 10)
                .firstElement()
                .toSingle()
                .compose(getApiExecutor());
    }

    public static class Params {
        private String source;

        public Params(String source) {
            this.source = source;
        }
    }
}
