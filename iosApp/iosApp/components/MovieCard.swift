//
//  MovieCard.swift
//  MoviesBudget
//
//  Created by admin on 2022. 01. 13..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct MovieCard: View {
    var budget: Int32
    var posterPath: String
    var body: some View {
        VStack {
            AsyncImage(url: URL(string: "https://image.tmdb.org/t/p/w500\(posterPath)")!, placeholder: {ProgressView()},
                       image: {
                Image(uiImage: $0)
                    .resizable()
            })
                .scaledToFill()
                .frame(maxWidth: .infinity , maxHeight: 180)
                .clipped()
            
            VStack {
                Text(budget.formatBudget())
                    .foregroundColor(Color("secondary"))
            }
            .padding(.vertical)
            .frame(maxWidth: .infinity)
            .border(Color("secondary"))
            .cornerRadius(15)
            
        }
        .overlay(RoundedRectangle(cornerRadius: 15, style: .continuous).stroke(Color("secondary")))
        .frame(width: 150, height: 240)
        .cornerRadius(15)
    }
}

extension Int32 {
    func formatBudget() -> String {
        if (self == 0) {
            return "No budget data"
        }
        return "\(self.formattedWithSeparator) $"
    }
}

extension Formatter {
    static let withSeparator: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = ","
        return formatter
    }()
}

extension Numeric {
    var formattedWithSeparator: String { Formatter.withSeparator.string(for: self) ?? "" }
}
