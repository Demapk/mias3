/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { SpecialityComponent } from '../../../../../../main/webapp/app/entities/speciality/speciality.component';
import { SpecialityService } from '../../../../../../main/webapp/app/entities/speciality/speciality.service';
import { Speciality } from '../../../../../../main/webapp/app/entities/speciality/speciality.model';

describe('Component Tests', () => {

    describe('Speciality Management Component', () => {
        let comp: SpecialityComponent;
        let fixture: ComponentFixture<SpecialityComponent>;
        let service: SpecialityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [SpecialityComponent],
                providers: [
                    SpecialityService
                ]
            })
            .overrideTemplate(SpecialityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpecialityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Speciality(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.specialities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
