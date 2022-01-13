//
//  MovieListScreen.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import domain
import features

struct MovieListScreen: View {
    @StateObject var proxy: MovieListScreenProxy
    var body: some View {
        MovieListScreenImpl(proxy: proxy)
            .onAppear(perform: proxy.onViewCreated)
            .onDisappear(perform: proxy.onStop)
    }
}

struct MovieListScreenImpl: View {
    @StateObject var proxy: MovieListScreenProxy
    @State var didSearch: Bool = false
    
    var body: some View {
        let state = proxy.state
        
        ScrollView {
            if(state != nil) {
                VStack {
                    SearchBar(query: state!.queryString ?? "", onChange: { query in proxy.sendWish(wish: MovieListViewModel.WishSetQueryString(queryString: query))}, onSearch: {
                        proxy.sendWish(wish: MovieListViewModel.WishSearchForMovies())
                        self.didSearch = true
                    }).padding()
                    
                    
                    if(self.didSearch) {
                        if(state!.searchedMoviesList != nil) {
                            VStack(alignment: .leading) {
                                Text("Searched movies")
                                    .bold()
                                    .font(.title)
                                    .padding(.leading, 24)
                                
                                ScrollView(.horizontal, showsIndicators: false) {
                                    LazyHStack(spacing: 12) {
                                        ForEach(state!.searchedMoviesList!, id: \.self) { movie in
                                            NavigationLink(destination: MovieDetailsScreen(posterPath: movie.posterPath ?? "", budget: movie.budget, title: movie.title, rating: movie.voteAverage)) {
                                                MovieCard(budget: movie.budget, posterPath: movie.posterPath!)
                                            }
                                        }
                                    }
                                    .padding(.leading, 12)
                                }
                            }
                        } else {
                            ProgressView()
                                .padding(.vertical, 36)
                        }
                    }
                    
                    if(state!.popularMoviesList != nil) {
                        VStack(alignment: .leading) {
                            Text("Popular movies")
                                .bold()
                                .font(.title)
                                .padding(.leading, 24)
                            
                            ScrollView(.horizontal, showsIndicators: false) {
                                LazyHStack(spacing: 12) {
                                    ForEach(state!.popularMoviesList!, id: \.self) { movie in
                                        NavigationLink(destination: MovieDetailsScreen(posterPath: movie.posterPath ?? "", budget: movie.budget, title: movie.title, rating: movie.voteAverage)) {
                                            MovieCard(budget: movie.budget, posterPath: movie.posterPath!)
                                        }
                                    }
                                }
                                .padding(.leading, 12)
                            }
                        }
                    } else {
                        ProgressView()
                            .padding(.vertical, 36)
                    }
                    
                    if(state!.nowPlayingMoviesList != nil) {
                        VStack(alignment: .leading) {
                            Text("Now playing")
                                .bold()
                                .font(.title)
                                .padding(.leading, 24)
                            ScrollView(.horizontal, showsIndicators: false) {
                                LazyHStack(spacing: 12) {
                                    ForEach(state!.nowPlayingMoviesList!, id: \.self) { movie in
                                        NavigationLink(destination: MovieDetailsScreen(posterPath: movie.posterPath ?? "", budget: movie.budget, title: movie.title, rating: movie.voteAverage)) {
                                            MovieCard(budget: movie.budget, posterPath: movie.posterPath!)
                                        }
                                    }
                                }
                                .padding(.leading, 12)
                            }
                        }
                    } else {
                        ProgressView()
                    }
                }
                .padding(.top, 36)
            }
        }
    }
}
