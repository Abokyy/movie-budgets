//
//  MovieDetailsScreen.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct MovieDetailsScreen: View {
    var posterPath: String
    var budget: Int32
    var title: String
    var rating: Double
    var body: some View {
        VStack {
            AsyncImage(url: URL(string: "https://image.tmdb.org/t/p/w500\(posterPath)")!, placeholder: {ProgressView()},
                       image: {
                Image(uiImage: $0)
                    .resizable()
            })
                .scaledToFill()
                .frame(maxWidth: .infinity , maxHeight: 380)
                .clipped()
            
            Text(title)
                .font(.headline)
            Text("Budget: \(budget.formatBudget())")
                .font(.title)
            HStack {
                Image("star")
                Text("Rating: \(String(rating))")
            }
            Spacer()
        }
        .navigationBarTitle(title)
    }
}
