import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Speciality } from './speciality.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Speciality>;

@Injectable()
export class SpecialityService {

    private resourceUrl =  SERVER_API_URL + 'api/specialities';

    constructor(private http: HttpClient) { }

    create(speciality: Speciality): Observable<EntityResponseType> {
        const copy = this.convert(speciality);
        return this.http.post<Speciality>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(speciality: Speciality): Observable<EntityResponseType> {
        const copy = this.convert(speciality);
        return this.http.put<Speciality>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Speciality>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Speciality[]>> {
        const options = createRequestOption(req);
        return this.http.get<Speciality[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Speciality[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Speciality = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Speciality[]>): HttpResponse<Speciality[]> {
        const jsonResponse: Speciality[] = res.body;
        const body: Speciality[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Speciality.
     */
    private convertItemFromServer(speciality: Speciality): Speciality {
        const copy: Speciality = Object.assign({}, speciality);
        return copy;
    }

    /**
     * Convert a Speciality to a JSON which can be sent to the server.
     */
    private convert(speciality: Speciality): Speciality {
        const copy: Speciality = Object.assign({}, speciality);
        return copy;
    }
}
