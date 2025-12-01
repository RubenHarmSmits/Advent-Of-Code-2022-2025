const EVENTS = [
    {
        distance: "DTS Ouderkerk Sprint",
        eventId: '7232306377607360512',
        distanceId: '490535',
        participants: 256,
        name: "Ruben Smits",
        gender: 1,
        ageGroup: "M14"
    },
    {
        distance: "DTS Ouderkerk Olympic",
        eventId: '7232306377607360512',
        distanceId: '490536',
        participants: 315,
        name: "Arnout Hemel",
        gender: 1,
        ageGroup: "M50"
    },
    {
        distance: "TRI Amsterdam Half",
        eventId: '7204124571736212736',
        distanceId: '489858',
        participants: 395,
        name: "Ruben Smits",
        gender: 2
    },
    {
        distance: "TRI Almere Standard",
        eventId: '7193612102416105216',
        distanceId: '489464',
        participants: 327,
        name: "Ruben Smits",
        gender: 2
    }
]
const LEGS = ['Swim ', 'T1   ', 'Bike ', 'T2   ', 'Run  ']

const findClassifications = async (event) => {
    let all = []
    for (let i = 0; i < event.participants; i += 50) {
        all.push(await getResults(event.eventId, event.distanceId, i))
    }
    const results = all
        .flat()
        .map(it => {
            return {
                gender: it.classification.gender,
                ageGroup: it.classification.category,
                name: it.athlete.name,
                legs: it.classification.legs.map(it => it.legDuration)
            }
        })
    const overallResult = results
        .map(person => person.name).indexOf(event.name) + 1

    const genderResult = results
        .filter(person => person.gender === event.gender)
        .map(person => person.name).indexOf(event.name) + 1

    const ageGroupResult = results
        .filter(person => person.ageGroup === event.ageGroup)
        .map(person => person.name).indexOf(event.name) + 1

    console.log(event.name)
    console.log(event.distance)
    console.log(`----------------------------`)
    console.log(`       All   |Gender|Agegroup`)

    LEGS.forEach((leg, i) => {
        const result = results.filter(it => it.legs[i] !== '00:00:00')
            .sort((first, second) => first.legs[i].localeCompare(second.legs[i]))
        const overall = result.map(person => person.name)
            .indexOf(event.name) + 1
        const gender = result
            .filter(person => person.gender === event.gender)
            .map(person => person.name)
            .indexOf(event.name) + 1
        const ageGroup = result
            .filter(person => person.ageGroup === event.ageGroup)
            .map(person => person.name)
            .indexOf(event.name) + 1

        console.log(`${leg}  ${(String(overall) + "   ").substring(0,3)}   | ${(String(gender)+"  ").substring(0,3)}  | ${ageGroup}`)
    })
    console.log()
    console.log(`total: ${overallResult}   | ${genderResult}   | ${ageGroupResult}`)
}

const getResults = async (eventId, distanceId, offset) => {
    const url = `https://eventresults-api.sporthive.com/api/events/${eventId}/races/${distanceId}/classifications/search?count=50&offset=${offset}`
    const results = await fetch(url);
    return (await results.json()).fullClassifications
}

findClassifications(EVENTS[0])